package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.model.Customer;

import java.util.*;

public class CustomerGenerator {

    private static final String[] FIRST_NAMES = {"John", "Jane", "David", "Emily", "Michael", "Sarah", "Aga", "Alan", "Marek", "Piotr", "Katarzyna"};
    private static final String[] LAST_NAMES = {"Doe", "Smith", "Johnson", "Davis", "Wilson", "Brown", "Kowalski", "Kulka", "Patyk", "Partyka", "Mordka"};
    private static final String[] EMAIL_DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com", "onet.pl", "interia.pl", "money.pl", "bankier.pl"};

    public static Customer generateRandomCustomer(Address address) {
        Random random = new Random();
        Customer customer = new Customer();

        customer.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
        customer.setLastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);

        customer.setEmail(customer.getFirstName().toLowerCase() + "." + customer.getLastName().toLowerCase() + "@" + EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)]);
        customer.setPhone("+12 345-678-903");
        customer.setReservations(null);

        return customer;
    }

}
