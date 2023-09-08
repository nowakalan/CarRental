package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.Role;

import java.util.Random;

public class CustomerGenerator {

    private static final String[] FIRST_NAMES = {"John", "Jane", "David", "Emily", "Michael", "Sarah"};
    private static final String[] LAST_NAMES = {"Doe", "Smith", "Johnson", "Davis", "Wilson", "Brown"};
    private static final String[] EMAIL_DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};

    public static Customer generateRandomCustomer(Address address) {
        Random random = new Random();
        Customer customer = new Customer();

        customer.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
        customer.setLastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);
        customer.setEmail(customer.getFirstName().toLowerCase() + "." + customer.getLastName().toLowerCase() + "@" + EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)]);
        customer.setPassword(customer.getFirstName().toLowerCase());
        customer.setRole(Role.ROLE_CUSTOMER);
        customer.setPhone("123-456-7890");
        customer.setReservations(null);

        return customer;
    }
}
