package tk.slaaavyn.redshark.dto.device;

import tk.slaaavyn.redshark.dto.user.UserResponseDto;
import tk.slaaavyn.redshark.model.Device;
import tk.slaaavyn.redshark.model.TaskType;

import java.util.Arrays;

public class DeviceDto {
    private Long id;
    private String name;
    private UserResponseDto owner;
    private TaskType[] availableTaskTypes;

    public static DeviceDto toDto(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setName(device.getName());
        deviceDto.setOwner(UserResponseDto.toDto(device.getUser()));
        deviceDto.setAvailableTaskTypes(TaskType.values());

        return deviceDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserResponseDto getOwner() {
        return owner;
    }

    public void setOwner(UserResponseDto owner) {
        this.owner = owner;
    }

    public TaskType[] getAvailableTaskTypes() {
        return availableTaskTypes;
    }

    public void setAvailableTaskTypes(TaskType[] availableTaskTypes) {
        this.availableTaskTypes = availableTaskTypes;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", availableJobTypes=" + Arrays.toString(availableTaskTypes) +
                '}';
    }
}
