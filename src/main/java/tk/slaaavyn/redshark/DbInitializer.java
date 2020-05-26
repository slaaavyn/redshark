package tk.slaaavyn.redshark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tk.slaaavyn.redshark.model.Device;
import tk.slaaavyn.redshark.model.Role;
import tk.slaaavyn.redshark.model.User;
import tk.slaaavyn.redshark.repository.DeviceRepository;
import tk.slaaavyn.redshark.repository.RoleRepository;
import tk.slaaavyn.redshark.repository.UserRepository;
import tk.slaaavyn.redshark.security.SecurityConstants;

@Component
public class DbInitializer implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    @Value("${default.admin.username}")
    private String defaultUsername;

    @Value("${default.admin.password}")
    private String defaultPassword;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DbInitializer(RoleRepository roleRepository, UserRepository userRepository,
                         DeviceRepository deviceRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initRoles();
        initAdmin();
        initAdminDevices();
    }

    private void initRoles() {
        if(roleRepository.findAll().size() != 0) {
            return;
        }

        Role roleAdmin = new Role();
        roleAdmin.setName(SecurityConstants.ROLE_ADMIN);
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName(SecurityConstants.ROLE_USER);
        roleRepository.save(roleUser);

        logger.info("ROLES has been initialized");
    }

    private void initAdmin() {
        if (userRepository.findAllByRole_Name(SecurityConstants.ROLE_ADMIN).size() != 0) {
            return;
        }

        Role roleAdmin = roleRepository.findRoleByName(SecurityConstants.ROLE_ADMIN);
        if (roleAdmin == null) {
            initRoles();
            roleAdmin = roleRepository.findRoleByName(SecurityConstants.ROLE_ADMIN);
        }

        User user = new User();
        user.setUsername(defaultUsername);
        user.setPassword(passwordEncoder.encode(defaultPassword));
        user.setRole(roleAdmin);
        user.setFirstName("Admin");
        user.setLastName("Admin");
        userRepository.save(user);

        logger.info("ADMIN has been initialized");
    }

    private void initAdminDevices() {
        User user = userRepository.findUserById(1);

        if(user != null && deviceRepository.findAllByUser_id(user.getId()).size() != 0) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            Device device = new Device();
            device.setName("AdminDevice" + i);
            device.setUser(user);

            deviceRepository.save(device);
        }

        logger.info("Devices has been initialized");
    }
}