package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
