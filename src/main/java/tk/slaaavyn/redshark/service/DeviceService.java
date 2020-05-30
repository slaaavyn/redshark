package tk.slaaavyn.redshark.service;

import tk.slaaavyn.redshark.model.Device;

import java.util.List;

public interface DeviceService {
    Device createDevice(long deviceOwnerId, String deviceName);

    List<Device> getAllUserDevices(long userId);

    List<Device> getAllDevices();

    Device getDevice(long deviceId);

    Device updateDeviceName(long ownerId, long deviceId, String deviceName);
}
