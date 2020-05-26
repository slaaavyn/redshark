package tk.slaaavyn.redshark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.redshark.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    User findUserByUsername(String username);
    List<User> findAllByRole_Id(long id);
    List<User> findAllByRole_Name(String roleName);
}
