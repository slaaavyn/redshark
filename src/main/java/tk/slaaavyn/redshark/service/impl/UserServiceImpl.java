package tk.slaaavyn.redshark.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.slaaavyn.redshark.dto.user.UpdatePasswordDto;
import tk.slaaavyn.redshark.model.Role;
import tk.slaaavyn.redshark.model.User;
import tk.slaaavyn.redshark.repository.DeviceRepository;
import tk.slaaavyn.redshark.repository.RoleRepository;
import tk.slaaavyn.redshark.repository.UserRepository;
import tk.slaaavyn.redshark.security.SecurityConstants;
import tk.slaaavyn.redshark.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DeviceRepository deviceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           DeviceRepository deviceRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.deviceRepository = deviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User incomingUser) {
        if (isInvalidUserData(incomingUser) || isUsernameExist(incomingUser.getUsername())) {
            return null;
        }

        Role role = roleRepository.findRoleByName(incomingUser.getRole().getName());
        if (role == null) {
            return null;
        }

        incomingUser.setRole(role);
        incomingUser.setPassword(passwordEncoder.encode(incomingUser.getPassword()));

        return userRepository.save(incomingUser);

    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByRole_Name(SecurityConstants.ROLE_USER);
    }

    @Override
    public List<User> getAllAdmins() {
        return userRepository.findAllByRole_Name(SecurityConstants.ROLE_ADMIN);
    }

    @Override
    public User getById(long userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User updateUserRole(long userId, String roleName) {
        User user = userRepository.findUserById(userId);
        Role newRole = roleRepository.findRoleByName(roleName);

        if (user == null || roleName == null) {
            return null;
        }

        switch (user.getRole().getName()) {
            case SecurityConstants.ROLE_ADMIN:
                if (!newRole.getName().equals(SecurityConstants.ROLE_ADMIN) && isNotLastAdmin(user)) {
                    user.setRole(newRole);
                }
                break;

            case SecurityConstants.ROLE_USER:
                user.setRole(newRole);
                break;
        }

        return userRepository.save(user);
    }

    @Override
    public boolean updatePassword(long userId, UpdatePasswordDto passwordDto) {
        User user = userRepository.findUserById(userId);

        if (user == null || passwordDto == null
                || passwordDto.getOldPassword() == null || passwordDto.getNewPassword() == null) {
            return false;
        }

        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())){
            return false;
        }

        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);

        return true;
    }

    private boolean isUsernameExist(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    private boolean isInvalidUserData(User user) {
        return user == null || user.getUsername() == null || user.getPassword() == null
                || user.getRole() == null || user.getRole().getName() == null
                || user.getFirstName() == null || user.getLastName() == null;
    }

    private boolean isNotLastAdmin(User user) {
        return !user.getRole().getName().equals(SecurityConstants.ROLE_ADMIN)
                || userRepository.findAllByRole_Name(SecurityConstants.ROLE_ADMIN).size() != 1;
    }
}
