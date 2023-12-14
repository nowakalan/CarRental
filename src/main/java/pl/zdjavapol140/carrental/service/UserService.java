package pl.zdjavapol140.carrental.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.AdminRepository;
import pl.zdjavapol140.carrental.repository.CustomerRepository;
import pl.zdjavapol140.carrental.repository.EmployeeRepository;
import pl.zdjavapol140.carrental.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, AdminRepository adminRepository) {

        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
    }


    public boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
    }



    // Metoda sprawdzająca rolę użytkownika
    public boolean userHasRole(User user, Role roleName) {
        if (user.getRole().equals(roleName)) {
            return true;
        }
        return false;
    }
}
