package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.Role;
import pl.zdjavapol140.carrental.model.User;
import pl.zdjavapol140.carrental.repository.AddressRepository;
import pl.zdjavapol140.carrental.repository.CustomerRepository;
import pl.zdjavapol140.carrental.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository, AddressRepository addressRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Customer addNewCustomer(String firstName,
                                   String lastName,
                                   String email,
                                   String phone) {

        if (customerRepository.findByEmail(email).isPresent()) {

            throw new RuntimeException("User with this email address already exists");
        }
        Customer customer = new Customer(
                null,
                firstName,
                lastName,
                email,
                phone,
                null,
                new ArrayList<>(),
                null);

        String encryptedPassword = passwordEncoder.encode(customer.getFirstName().toLowerCase());

        User user = new User(customer.getEmail(), encryptedPassword, Role.ROLE_CUSTOMER);

        userRepository.save(user);
        customer.setUser(user);

        Address address = new Address();
        addressRepository.save(address);
        customer.setAddress(address);


        customerRepository.save(customer);


        return customer;

    }

    @Transactional
    public boolean addCustomer(Customer customer) {

        customerRepository.save(customer);
        return true;
    }

    @Transactional
    public boolean deleteCustomer(Long customerId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("Customer id not found");
        }
        customerRepository.delete(optionalCustomer.get());
        return true;
    }

    @Transactional
    public boolean updateCustomer(Customer customer) {

        if (customerRepository.findById(customer.getId()).isEmpty()) {
            throw new RuntimeException("Customer id not found");
        }
        customerRepository.save(customer);
        return true;
    }

//    @Transactional
//    public boolean setCustomerAddress(Long customerId, Long addressId) {
//
//        Customer customer = this.findCustomerById(customerId);
//
//        Address address = addressRepository.
//
//        customer.setAddress(addressRepository.findById(addressId));
//        customerRepository.save(customer);
//
//        return true;
//    }

    public Customer findCustomerById(Long id) {

        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer id not found"));
    }


    public Customer findCustomerByEmail(String email) {

        return customerRepository.findCustomerByEmail(email);
    }

    public Customer findCustomersByEmail(String email) {

        return customerRepository.findCustomersByEmail(email);
    }
}