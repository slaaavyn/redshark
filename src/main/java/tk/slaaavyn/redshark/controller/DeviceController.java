package tk.slaaavyn.redshark.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.redshark.config.EndpointConstants;
import tk.slaaavyn.redshark.dto.device.DeviceDto;
import tk.slaaavyn.redshark.model.Device;
import tk.slaaavyn.redshark.security.jwt.JwtUser;
import tk.slaaavyn.redshark.service.DeviceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.DEVICE_ENDPOINT)
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create new device for authorized user . Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<DeviceDto> createDevice(@RequestParam("deviceName") String deviceName) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Device device = deviceService.createDevice(jwtUser.getId(), deviceName);
        if (device == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(DeviceDto.toDto(device));
    }

    @GetMapping
    @ApiOperation(value = "Get authorized user device by id. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<DeviceDto> getAllDevicesOfUser(@RequestParam(defaultValue = "0") Long deviceId) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Device device = deviceService.getDevice(deviceId);

        if(!device.getUser().getId().equals(jwtUser.getId())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(DeviceDto.toDto(device));
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Get all authorised user devices. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        List<Device> deviceList = deviceService.getAllUserDevices(jwtUser.getId());
        List<DeviceDto> dtoList = new ArrayList<>();

        deviceList.forEach(device -> dtoList.add(DeviceDto.toDto(device)));

        return ResponseEntity.ok(dtoList);
    }

    @PutMapping
    @ApiOperation(value = "Update authorized user device name. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<DeviceDto> updateDevice(@RequestParam("deviceId") Long deviceId, @RequestParam("deviceName") String deviceName) {
        if(deviceId == null || deviceName == null) {
            return ResponseEntity.badRequest().build();
        }

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Device device = deviceService.updateDeviceName(jwtUser.getId(), deviceId, deviceName);
        if (device == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(DeviceDto.toDto(device));
    }

    @DeleteMapping
    @ApiOperation(value = "Delete authorized user device by id. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<Boolean> deleteDevice(@RequestParam("deviceId") Long deviceId) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if(deviceId == null || !deviceService.removeDevice(jwtUser.getId(), deviceId)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(true);
    }
}
