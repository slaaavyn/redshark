package tk.slaaavyn.redshark.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.redshark.model.Device;
import tk.slaaavyn.redshark.model.User;
import tk.slaaavyn.redshark.repository.DeviceRepository;
import tk.slaaavyn.redshark.repository.FileRepository;
import tk.slaaavyn.redshark.repository.UserRepository;
import tk.slaaavyn.redshark.service.DeviceService;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, UserRepository userRepository,
                             FileRepository fileRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Device createDevice(long deviceOwnerId, String deviceName) {
        User owner = userRepository.findUserById(deviceOwnerId);
        if (owner == null) {
            return null;
        }

        Device device = new Device();
        device.setName(deviceName);
        device.setUser(owner);

        return deviceRepository.save(device);
    }

    @Override
    public List<Device> getAllUserDevices(long userId) {
        return deviceRepository.findAllByUser_id(userId);
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDevice(long deviceId) {
        return deviceRepository.findDeviceById(deviceId);
    }

    @Override
    public Device updateDeviceName(long ownerId, long deviceId, String deviceName) {
        Device device = deviceRepository.findDeviceById(deviceId);
        if (device == null || !device.getUser().getId().equals(ownerId)) {
            return null;
        }

        device.setName(deviceName);

        return deviceRepository.save(device);
    }
}
