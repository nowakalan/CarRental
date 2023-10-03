package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.User;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByEmail(String email);
    List<Customer> findCustomersByAddress_City(String cityName);

    List<Customer> findCustomersByAddress_Country(String country);

    List<Customer> findCustomersByAddress_PostalCode(String postalCode);

    List<Customer> findCustomersByLastName(String lastName);

    List<Customer> findCustomersByLastNameAndFirstName(String lastName, String firstName);

    List<Customer> findCustomersByPhone(String phone);
    Customer findCustomerByUser(User user);

    Customer findCustomersByEmail(String email);
}
