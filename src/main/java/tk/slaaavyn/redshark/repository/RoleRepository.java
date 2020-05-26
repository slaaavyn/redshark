package tk.slaaavyn.redshark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.slaaavyn.redshark.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long roleId);
    Role findRoleByName(String roleName);
}