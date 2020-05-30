package tk.slaaavyn.redshark.service;

import tk.slaaavyn.redshark.dto.task.TaskCreateDto;
import tk.slaaavyn.redshark.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(TaskCreateDto taskDto, Long ownerId);

    List<Task> getAllUserTask(Long ownerId);

    List<Task> getAllTasks();
}
