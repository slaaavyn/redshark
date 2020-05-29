package tk.slaaavyn.redshark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.redshark.model.File;
import tk.slaaavyn.redshark.model.FileType;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    File findByIdAndDevice_Id(Long fileId, Long deviceId);
    File findByIdAndDevice_IdAndFileType(Long fileId, Long deviceId, FileType fileType);
    File findByDevice_IdAndParent_IdAndName(Long deviceId, Long parentId, String fileName);
    List<File> findAllByDevice_IdAndParent_Id(Long deviceId, Long parentId);
    List<File> findAllByDevice_IdAndNameAndFileTypeAndDevice_User_Id(Long deviceId, String name, FileType fileType, Long userId);
    void deleteAllByDevice_Id(Long deviceId);
}
