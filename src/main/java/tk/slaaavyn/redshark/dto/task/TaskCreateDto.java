package tk.slaaavyn.redshark.dto.task;

import tk.slaaavyn.redshark.model.TaskType;

import java.util.List;

public class TaskCreateDto {
    private long deviceId;
    private TaskType taskType;
    private List<Long> fileIdList;

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public List<Long> getFileIdList() {
        return fileIdList;
    }

    public void setFileIdList(List<Long> fileIdList) {
        this.fileIdList = fileIdList;
    }

    @Override
    public String toString() {
        return "TaskCreateDto{" +
                "deviceId=" + deviceId +
                ", taskType=" + taskType +
                ", fileIdList=" + fileIdList +
                '}';
    }
}
