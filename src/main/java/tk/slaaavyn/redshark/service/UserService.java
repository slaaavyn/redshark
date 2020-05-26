package tk.slaaavyn.redshark.service;

import tk.slaaavyn.redshark.dto.user.UpdatePasswordDto;
import tk.slaaavyn.redshark.model.User;

import java.util.List;

public interface UserService {
    User createUser(User incomingUser);

    List<User> getAll();

    List<User> getAllUsers();

    List<User> getAllAdmins();

    User getById(long userId);

    User getByUsername(String username);

    User updateUserRole(long userId, String roleName);

    boolean updatePassword(long userId, UpdatePasswordDto passwordDto);

    boolean removeById(long userId);
}
