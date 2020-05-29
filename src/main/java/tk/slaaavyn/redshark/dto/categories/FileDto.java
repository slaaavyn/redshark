package tk.slaaavyn.redshark.dto.categories;

import tk.slaaavyn.redshark.model.File;
import tk.slaaavyn.redshark.model.FileType;

public class FileDto {
    private Long id;
    private Long deviceId;
    private String name;
    private Long parentId;
    private String parentName;
    private FileType fileType;

    protected FileDto() {}

    protected FileDto(File file) {
        this.id = file.getId();
        this.deviceId = file.getDevice().getId();
        this.name = file.getName();
        this.parentId = file.getParent() != null ? file.getParent().getId() : null;
        this.parentName = file.getParent() != null ? file.getParent().getName() : null;
        this.fileType = file.getFileType();
    }

    public static FileDto toDto(File file) {
        return new FileDto(file);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", fileType=" + fileType +
                '}';
    }
}
