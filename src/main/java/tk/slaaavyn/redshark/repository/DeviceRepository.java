package tk.slaaavyn.redshark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.redshark.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceById(long id);
}
