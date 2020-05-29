package tk.slaaavyn.redshark.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.redshark.model.Device;
import tk.slaaavyn.redshark.model.File;
import tk.slaaavyn.redshark.model.FileType;
import tk.slaaavyn.redshark.repository.DeviceRepository;
import tk.slaaavyn.redshark.repository.FileRepository;
import tk.slaaavyn.redshark.service.FileService;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final DeviceRepository deviceRepository;
    private final FileRepository fileRepository;

    public FileServiceImpl(DeviceRepository deviceRepository, FileRepository fileRepository) {
        this.deviceRepository = deviceRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public File createFile(Long deviceId, Long ownerId, Long parentId, String fileName, FileType fileType) {
        Device device = deviceRepository.findDeviceByIdAndUser_Id(deviceId, ownerId);
        if (device == null || !device.getUser().getId().equals(ownerId)) {
            return null;
        }

        File parentDirectory = fileRepository.findByIdAndDevice_IdAndFileType(parentId, deviceId, FileType.DIRECTORY);
        if (isFileAlreadyExist(deviceId, parentId, fileName)) {
            return null;
        }

        File file = new File();
        file.setName(fileName);
        file.setDevice(device);
        file.setParent(parentDirectory);
        file.setFileType(fileType);

        return fileRepository.save(file);
    }

    @Override
    public List<File> getRootFiles(Long deviceId, Long ownerId) {
        Device device = deviceRepository.findDeviceByIdAndUser_Id(deviceId, ownerId);
        if (device == null || !device.getUser().getId().equals(ownerId)) {
            return null;
        }

        return fileRepository.findAllByDevice_IdAndParent_Id(device.getId(), null);
    }

    @Override
    public File getFile(Long fileId, Long deviceId, Long ownerId) {
        Device device = deviceRepository.findDeviceByIdAndUser_Id(deviceId, ownerId);
        if (device == null || !device.getUser().getId().equals(ownerId)) {
            return null;
        }

        return fileRepository.findByIdAndDevice_Id(fileId, deviceId);
    }

    @Override
    public List<File> getDirectoryChildren(File parentFile) {
        if (parentFile == null) {
            return null;
        }

        return fileRepository.findAllByDevice_IdAndParent_Id(parentFile.getDevice().getId(), parentFile.getId());
    }

    @Override
    public List<File> findFilesInDevice(Long deviceId, String fileName, Long ownerId) {
        return fileRepository.findAllByDevice_IdAndNameAndFileTypeAndDevice_User_Id(
                deviceId, fileName, FileType.FILE, ownerId);
    }

    @Override
    public File moveFile(Long deviceId, Long fileId, Long destinationFileId, Long ownerId) {
        File file = fileRepository.findByIdAndDevice_Id(fileId, deviceId);
        File destinationFile = fileRepository.findByIdAndDevice_IdAndFileType(destinationFileId, deviceId, FileType.DIRECTORY);

        if (file == null || !file.getDevice().getUser().getId().equals(ownerId)
                || destinationFile == null || isFileAlreadyExist(deviceId, destinationFileId, file.getName())) {
            return null;
        }

        file.setParent(destinationFile);

        return fileRepository.save(file);
    }

    private boolean isFileAlreadyExist(Long deviceId, Long parentId, String fileName) {
        return fileRepository.findByDevice_IdAndParent_IdAndName(deviceId, parentId, fileName) != null;
    }
}
