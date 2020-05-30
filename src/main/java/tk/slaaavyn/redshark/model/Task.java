package tk.slaaavyn.redshark.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @ManyToOne(targetEntity = Device.class, fetch = FetchType.LAZY, optional = false)
    private Device device;

    @Column(name = "task_type", nullable = false)
    private TaskType taskType;

    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Task_File",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "file_id") }
    )
    private List<File> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", device=" + device +
                ", taskType=" + taskType +
                ", status=" + status +
                ", files size=" + (files != null ? files.size() : null) +
                '}';
    }
}
