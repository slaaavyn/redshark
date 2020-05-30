package tk.slaaavyn.redshark.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.redshark.config.EndpointConstants;
import tk.slaaavyn.redshark.dto.task.TaskCreateDto;
import tk.slaaavyn.redshark.dto.task.TaskResponseDto;
import tk.slaaavyn.redshark.model.Task;
import tk.slaaavyn.redshark.security.jwt.JwtUser;
import tk.slaaavyn.redshark.service.TaskService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.TASK_ENDPOINT)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Created task for registered users. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskCreateDto taskDto) {

        if (taskDto == null || taskDto.getTaskType() == null || taskDto.getFileIdList() == null) {
            return ResponseEntity.badRequest().build();
        }

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Task result = taskService.createTask(taskDto, jwtUser.getId());
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(TaskResponseDto.toDto(result));
    }

    @GetMapping
    @ApiOperation(value = "Get task list for registered users. Action as role: ADMIN, USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<List<TaskResponseDto>> getUserTasks() {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Task> taskList = taskService.getAllUserTask(jwtUser.getId());

        return ResponseEntity.ok(fromTaskToDtoList(taskList));
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Get task list for for any users. " +
            "If the userId is not specified, then all tasks will be displayed. " +
            "Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<List<TaskResponseDto>> getTasks(
            @RequestParam(name = "userId", required = false) Long userId ) {
        List<Task> taskList;

        if (userId != null) {
            taskList = taskService.getAllUserTask(userId);
        } else {
            taskList = taskService.getAllTasks();
        }

        return ResponseEntity.ok(fromTaskToDtoList(taskList));
    }

    private List<TaskResponseDto> fromTaskToDtoList(List<Task> taskList) {
        List<TaskResponseDto> result = new ArrayList<>();
        if (taskList == null) {
            return result;
        }

        taskList.forEach(task -> result.add(TaskResponseDto.toDto(task)));

        return result;
    }
}
