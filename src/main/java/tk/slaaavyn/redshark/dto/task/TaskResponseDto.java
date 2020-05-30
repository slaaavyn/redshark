package tk.slaaavyn.redshark.dto.task;

import tk.slaaavyn.redshark.dto.categories.FileDto;
import tk.slaaavyn.redshark.dto.categories.FileDtoFactory;
import tk.slaaavyn.redshark.model.Task;
import tk.slaaavyn.redshark.model.TaskStatus;
import tk.slaaavyn.redshark.model.TaskType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskResponseDto {
    private Long id;
    private Date creationDate;
    private Long deviceId;
    private TaskType taskType;
    private TaskStatus status;
    private List<FileDto> files;

    private TaskResponseDto() {}

    public static TaskResponseDto toDto(Task task) {
        TaskResponseDto response = new TaskResponseDto();
        response.setId(task.getId());
        response.setCreationDate(task.getCreationDate());
        response.setDeviceId(task.getDevice().getId());
        response.setTaskType(task.getTaskType());
        response.setStatus(task.getStatus());
        response.setFiles(new ArrayList<>());

        task.getFiles().forEach(file -> response.getFiles().add(FileDtoFactory.toDto(file)));

        return response;
    }

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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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

    public List<FileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileDto> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "TaskResponseDto{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", deviceId=" + deviceId +
                ", taskType=" + taskType +
                ", status=" + status +
                ", files size=" + files.size() +
                '}';
    }
}
