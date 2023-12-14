package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeesByBranch(Branch branch);

    Optional<Employee> findByEmail(String email);
    Employee findEmployeeByEmail(String email);
}
