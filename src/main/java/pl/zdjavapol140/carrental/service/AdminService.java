package pl.zdjavapol140.carrental.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Admin;
import pl.zdjavapol140.carrental.model.Role;
import pl.zdjavapol140.carrental.model.User;
import pl.zdjavapol140.carrental.repository.AdminRepository;
import pl.zdjavapol140.carrental.repository.UserRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminService(AdminRepository adminRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin addNewAdmin(String firstName,
                             String lastName,
                             String email) {

        if(adminRepository.findAdminByEmail(email).isPresent()){
            throw new RuntimeException("User with this email address already exists");
        }

        Admin admin = new Admin(
                null,
                firstName,
                lastName,
                email,
                null);

        String encryptedPassword = passwordEncoder.encode(admin.getFirstName().toLowerCase());

        User user = new User(admin.getEmail(), encryptedPassword, Role.ROLE_ADMIN);
        userRepository.save(user);

        admin.setUser(user);
        adminRepository.save(admin);

        return admin;
    }
}
