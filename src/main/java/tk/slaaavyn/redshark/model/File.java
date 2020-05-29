package tk.slaaavyn.redshark.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "File")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_id")
    private File parent;

    @ManyToOne(targetEntity = Device.class, fetch = FetchType.LAZY, optional = false)
    private Device device;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getParent() {
        return parent;
    }

    public void setParent(File parent) {
        this.parent = parent;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", parentId=" + (parent != null ? String.valueOf(parent.getId()) : null) +
                ", deviceId=" + device.getId() +
                ", name='" + name + '\'' +
                ", fileType=" + fileType +
                '}';
    }
}
