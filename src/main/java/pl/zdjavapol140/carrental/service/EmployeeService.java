package pl.zdjavapol140.carrental.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.BranchRepository;
import pl.zdjavapol140.carrental.repository.EmployeeRepository;
import pl.zdjavapol140.carrental.repository.UserRepository;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final BranchRepository branchRepository;
    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, BranchRepository branchRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.branchRepository = branchRepository;
    }

    public Employee addNewEmployee(String firstName,
                                   String lastName,
                                   String email,
                                   Job job,
                                   Branch branch) {

        if (employeeRepository.findByEmail(email).isPresent()) {

            throw new RuntimeException("User with this email address already exists");
        }
        Employee employee = new Employee(
                null,
                firstName,
                lastName,
                email,
                job,
                branch,
                null);

        // Hashowanie hasła przed zapisaniem do bazy danych
        String encryptedPassword = passwordEncoder.encode(employee.getFirstName().toLowerCase());

        User user = new User(employee.getEmail(), encryptedPassword, Role.ROLE_EMPLOYEE);

        userRepository.save(user);

        employee.setUser(user);

        employeeRepository.save(employee);


        return employee;
    }

    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }
}
