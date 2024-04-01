package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.User;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Customer findCustomersByEmail(String email);

    Customer findCustomerByEmail(String email);
}
