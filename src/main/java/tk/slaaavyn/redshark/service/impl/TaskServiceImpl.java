package tk.slaaavyn.redshark.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.redshark.dto.task.TaskCreateDto;
import tk.slaaavyn.redshark.model.Device;
import tk.slaaavyn.redshark.model.Task;
import tk.slaaavyn.redshark.model.TaskStatus;
import tk.slaaavyn.redshark.repository.DeviceRepository;
import tk.slaaavyn.redshark.repository.FileRepository;
import tk.slaaavyn.redshark.repository.TaskRepository;
import tk.slaaavyn.redshark.service.TaskService;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final DeviceRepository deviceRepository;
    private final FileRepository fileRepository;

    public TaskServiceImpl(TaskRepository taskRepository, DeviceRepository deviceRepository,
                           FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.deviceRepository = deviceRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Task createTask(TaskCreateDto taskDto, Long ownerId) {
        if (taskDto == null || taskDto.getFileIdList() == null || taskDto.getTaskType() == null || ownerId == null) {
            return null;
        }

        Device device = deviceRepository.findDeviceById(taskDto.getDeviceId());
        if (!device.getUser().getId().equals(ownerId)) {
            return null;
        }

        Task task = new Task();
        task.setDevice(device);
        task.setCreationDate(new Date());
        task.setTaskType(taskDto.getTaskType());
        task.setStatus(TaskStatus.PREPARING);
        task.setFiles(fileRepository.findAllById(taskDto.getFileIdList()));

        if(task.getFiles().size() == 0) {
            task.setFiles(fileRepository.findAllByDevice_IdAndParent_Id(device.getId(), null));
        }

        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllUserTask(Long ownerId) {
        return taskRepository.getAllByDevice_User_Id(ownerId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
