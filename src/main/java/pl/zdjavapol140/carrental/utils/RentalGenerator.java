package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Rental;

import java.util.Random;

public class RentalGenerator {

    private static final String[] RENTAL_NAMES = {"ABC Car Rental", "XYZ Rent-a-Car", "Quick Car Hire", "EasyDrive Rentals", "GoCar Now"};

    public static Rental generateRandomRental() {
        Random random = new Random();
        Rental rental = new Rental();

        rental.setName(RENTAL_NAMES[random.nextInt(RENTAL_NAMES.length)]);
        rental.setUrl("https://www." + rental.getName().toLowerCase().replaceAll(" ", "") + ".com");
        rental.setAddress(null);
        rental.setBranches(null);

        return rental;
    }

}
