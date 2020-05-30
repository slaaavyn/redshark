package tk.slaaavyn.redshark.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.redshark.config.EndpointConstants;
import tk.slaaavyn.redshark.dto.user.UserRequestDto;
import tk.slaaavyn.redshark.dto.user.UserResponseDto;
import tk.slaaavyn.redshark.model.User;
import tk.slaaavyn.redshark.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.USER_ENDPOINT)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create new user with role ADMIN or USER. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<UserResponseDto> createAdmin(@RequestBody UserRequestDto userDto) {
        User result = userService.createUser(userDto.fromDto());

        if(result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }

    @GetMapping
    @ApiOperation(value = "Get all registered users. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> result = new ArrayList<>();

        userService.getAll().forEach(user -> result.add(UserResponseDto.toDto(user)));

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "{id}")
    @ApiOperation(value = "Get registered user by id. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<UserResponseDto> getAdminById(@PathVariable(name = "id") Long id) {
        User user = userService.getById(id);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserResponseDto.toDto(user));
    }

    @PutMapping("/update-role/{id}")
    @ApiOperation(value = "Update user role. Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<UserResponseDto> updateAdminPassword(@PathVariable(name = "id") Long id,
                                                               @RequestParam("roleName") String roleName) {
        User result = userService.updateUserRole(id, roleName);

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserResponseDto.toDto(result));
    }
}
