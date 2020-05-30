package tk.slaaavyn.redshark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.redshark.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task getById(Long taskId);
    Task getByIdAndDevice_User_Id(Long taskId, Long userId);
    List<Task> getAllByDevice_User_Id(Long userId);
}
