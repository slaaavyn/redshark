package tk.slaaavyn.redshark.service;

import tk.slaaavyn.redshark.model.File;
import tk.slaaavyn.redshark.model.FileType;

import java.util.List;

public interface FileService {
    File createFile(Long deviceId, Long ownerId, Long parentId, String fileName, FileType fileType);

    List<File> getRootFiles(Long deviceId, Long ownerId);

    File getFile(Long fileId, Long deviceId, Long ownerId);

    List<File> getDirectoryChildren(File directoryFile);

    List<File> findFilesInDevice(Long deviceId, String fileName, Long ownerId);

    File moveFile(Long deviceId, Long fileId, Long destinationFileId, Long ownerId);
}
