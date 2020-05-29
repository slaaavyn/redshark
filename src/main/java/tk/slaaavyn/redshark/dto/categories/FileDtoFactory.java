package tk.slaaavyn.redshark.dto.categories;

import tk.slaaavyn.redshark.model.File;
import tk.slaaavyn.redshark.model.FileType;

import java.util.ArrayList;
import java.util.List;

public abstract class FileDtoFactory {
    public static FileDto toDto(File file) {
        return new FileDto(file);
    }

    public static FileDto toDto(File file, List<File> children) {
        return new DirectoryDto(file, children);
    }

    public static FileDto toRootDirectoryDto(long deviceId, List<File> files) {
        DirectoryDto directoryDto = new DirectoryDto();
        directoryDto.setFileType(FileType.DIRECTORY);
        directoryDto.setParentName("/");
        directoryDto.setDeviceId(deviceId);
        directoryDto.setChildren(new ArrayList<>());

        if(files != null) {
            files.forEach(file -> {
                directoryDto.getChildren().add(toDto(file));
            });
        }

        return directoryDto;
    }
}
