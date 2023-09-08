package pl.zdjavapol140.carrental.service;

import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.repository.CustomerRepository;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;


    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer Id not found"));
        customerRepository.delete(customer);
    }

    //TODO
    //Jak apdejtować obiekt w repo???
    //Trzeba skasować stary i zapisać nowy? Co z id?
    //Trzeba ustawić pola przez "set"? Czy to się samo zapisze w bazie?

    public void updateCustomer(Customer customer) {
        Customer customerToUpdate = customerRepository.findById(customer.getId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customerToUpdate);
        customerRepository.save(customerToUpdate);
    }
}
