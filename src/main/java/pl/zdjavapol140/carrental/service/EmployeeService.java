package pl.zdjavapol140.carrental.service;

import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.BranchRepository;
import pl.zdjavapol140.carrental.repository.EmployeeRepository;
import pl.zdjavapol140.carrental.repository.UserRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    private final BranchRepository branchRepository;
    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository, BranchRepository branchRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
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

        User user = new User(null, employee.getEmail(), employee.getFirstName().toLowerCase());
        userRepository.save(user);

        employee.setUser(user);

        employeeRepository.save(employee);


        return employee;

    }
}
