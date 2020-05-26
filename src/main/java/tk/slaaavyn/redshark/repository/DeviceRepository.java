package tk.slaaavyn.redshark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.redshark.model.Device;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceById(long id);
    List<Device> findAllByUser_id(long userId);
    void deleteAllByUser_Id(long userId);
}
