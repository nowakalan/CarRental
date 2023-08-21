package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
