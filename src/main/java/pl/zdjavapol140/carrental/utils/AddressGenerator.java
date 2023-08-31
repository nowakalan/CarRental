package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Address;

import java.util.Random;

public class AddressGenerator {

    private static final String[] COUNTRIES = {"Poland", "Germany", "France", "Spain", "Italy", "United Kingdom"};
    private static final String[] CITIES = {"Warsaw", "Berlin", "Paris", "Madrid", "Rome", "London"};
    private static final String[] POSTAL_CODES = {"01-100", "12-345", "34-567", "45-678", "56-789", "67-890"};
    private static final String[] DETAILS = {"123 Main Street", "456 Elm Avenue", "789 Oak Boulevard", "101 Pine Road", "202 Maple Lane"};

    public static Address generateRandomAddress() {
        Random random = new Random();
        Address address = new Address();

        address.setCountry(COUNTRIES[random.nextInt(COUNTRIES.length)]);
        address.setCity(CITIES[random.nextInt(CITIES.length)]);
        address.setPostalCode(POSTAL_CODES[random.nextInt(POSTAL_CODES.length)]);
        address.setDetails(DETAILS[random.nextInt(DETAILS.length)]);

        return address;
    }
}
