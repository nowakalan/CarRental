package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.repository.AddressRepository;
import pl.zdjavapol140.carrental.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    CustomerRepository customerRepository;
    AddressService addressService;

    public CustomerService(CustomerRepository customerRepository, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
    }


    public Customer createNewCustomer(String firstName,
                                      String lastName,
                                      String email,
                                      String phone) {

        if (customerRepository.findCustomerByEmail(email).isPresent()) {
            throw new RuntimeException("Customer with this email address already exists");
        }
        return new Customer(
                null,
                firstName,
                lastName,
                email,
                phone,
                null,
                new ArrayList<>());
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

    @Transactional
    public boolean setCustomerAddress(Long customerId, Long addressId) {

        Customer customer = this.findCustomerById(customerId);
        customer.setAddress(addressService.findAddressById(addressId));
        customerRepository.save(customer);

        return true;
    }

    public Customer findCustomerById(Long id) {

        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer id not found"));
    }

    public Customer findCustomerByEmail(String email) {

        return customerRepository.findCustomerByEmail(email).orElseThrow(() -> new RuntimeException("Customer id not found"));
    }

    public List<Customer> findCustomersByCityName(String city) {

        return customerRepository.findCustomersByAddress_City(city);
    }

    public List<Customer> findCustomersByCountryName(String country) {

        return customerRepository.findCustomersByAddress_Country(country);
    }

    public List<Customer> findCustomersByPostalCode(String postalCode) {

        return customerRepository.findCustomersByAddress_PostalCode(postalCode);
    }
    public List<Customer> findCustomersByLastName(String lastName) {

        return customerRepository.findCustomersByLastName(lastName);
    }
    public List<Customer> findCustomersByLastNameAndFirstName(String lastName, String firstName) {

        return customerRepository.findCustomersByLastNameAndFirstName(lastName, firstName);
    }
    public List<Customer> findCustomersByPhoneNumber(String phone) {

        return customerRepository.findCustomersByPhone(phone);
    }
}
