package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.CarSize;
import pl.zdjavapol140.carrental.model.CarTransmissionType;

import java.math.BigDecimal;
import java.util.Random;

public class CarGenerator {

    private static final String[] CAR_BRANDS = {"Fiat", "Renault", "Seat", "Skoda", "Volvo"};
    private static final String[] CAR_MODELS = {"500", "Clio", "Leon", "Octavia", "XC60"};
    private static final String[] CAR_COLORS = {"Red", "Blue", "Silver", "Black", "White", "Gray"};
    private static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final String IMAGE_DIRECTORY = "/images/";


    private static String generateUniqueRegistrationNumber() {
        Random random = new Random();
        StringBuilder registrationNumber = new StringBuilder();

        registrationNumber.append(LETTERS[random.nextInt(LETTERS.length)]);
        registrationNumber.append(LETTERS[random.nextInt(LETTERS.length)]);

        for (int i = 0; i < 5; i++) {
            registrationNumber.append(random.nextInt(10));
        }

        return registrationNumber.toString();
    }

    public static Car generateCarByType(String carType) {
        Random random = new Random();
        Car car = new Car();

        // Ustawienia dla wszystkich klas samochodow
        car.setTransmissionType(CarTransmissionType.values()[random.nextInt(CarTransmissionType.values().length)]);
        car.setProductionYear(random.nextInt(4) + 2020); // Losowe lata produkcji od 2020 do 2024
        car.setColor(CAR_COLORS[random.nextInt(CAR_COLORS.length)]);
        car.setMileage(random.nextInt(199999) + 1);
        car.setRegistrationNumber(generateUniqueRegistrationNumber());
        car.setRental(null);
        car.setReservations(null);

        // Dodatkowe ustawienia zależne od rodzaju samochodu
        switch (carType.toLowerCase()) {
            case "small":
                car.setBrand("Fiat");
                car.setModel("500");
                car.setSize(CarSize.SMALL);
                car.setPrice(CarSize.SMALL.getPrice());
                car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase() + ".png");
                break;
            case "economy":
                car.setBrand("Renault");
                car.setModel("Clio");
                car.setSize(CarSize.ECONOMY);
                car.setPrice(CarSize.ECONOMY.getPrice());
                car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase() + ".png");
                break;
            case "compact":
                car.setBrand("Seat");
                car.setModel("Leon");
                car.setSize(CarSize.COMPACT);
                car.setPrice(CarSize.COMPACT.getPrice());
                car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase() + ".png");
                break;
            case "estates":
                car.setBrand("Skoda");
                car.setModel("Octavia Combi");
                car.setSize(CarSize.ESTATES);
                car.setPrice(CarSize.ESTATES.getPrice());
                car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase().replace(" ", "-") + ".png");
                break;
            case "suv":
                car.setBrand("Volvo");
                car.setModel("XC60");
                car.setSize(CarSize.SUV);
                car.setPrice(CarSize.SUV.getPrice());
                car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase().replace(" ", "-") + ".png");
                break;
            default:
                // Domyślne ustawienia lub obsługa błędu
                // ...
        }

        return car;
    }

    public static Car generateSmallClassCar() {
        Random random = new Random();
        Car car = new Car();

        car.setBrand("Fiat");
        car.setModel("500");
        car.setSize(CarSize.SMALL);
        car.setTransmissionType(CarTransmissionType.values()[random.nextInt(CarTransmissionType.values().length)]);
        car.setProductionYear(random.nextInt(4) + 2020); // Losowe lata produkcji od 2020 do 2024
        car.setColor(CAR_COLORS[random.nextInt(CAR_COLORS.length)]);
        car.setMileage(random.nextInt(199999) + 1);
        car.setPrice(CarSize.SMALL.getPrice());
        car.setRegistrationNumber(generateUniqueRegistrationNumber());
        car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase() + ".png");
        car.setRental(null);
        car.setReservations(null);

        return car;
    }

    public static Car generateEconomyClassCar() {
        Random random = new Random();
        Car car = new Car();

        car.setBrand("Renault");
        car.setModel("Clio");
        car.setSize(CarSize.ECONOMY);
        car.setTransmissionType(CarTransmissionType.values()[random.nextInt(CarTransmissionType.values().length)]);
        car.setProductionYear(random.nextInt(4) + 2020); // Random production year from 2020 to 2024
        car.setColor(CAR_COLORS[random.nextInt(CAR_COLORS.length)]);
        car.setMileage(random.nextInt(199999) + 1);
        car.setPrice(CarSize.ECONOMY.getPrice());
        car.setRegistrationNumber(generateUniqueRegistrationNumber());
        car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase() + ".png");
        car.setRental(null);
        car.setReservations(null);

        return car;
    }

    public static Car generateCompactClassCar() {
        Random random = new Random();
        Car car = new Car();

        car.setBrand("Seat");
        car.setModel("Leon");
        car.setSize(CarSize.COMPACT);
        car.setTransmissionType(CarTransmissionType.values()[random.nextInt(CarTransmissionType.values().length)]);
        car.setProductionYear(random.nextInt(4) + 2020); // Random production year from 2020 to 2024
        car.setColor(CAR_COLORS[random.nextInt(CAR_COLORS.length)]);
        car.setMileage(random.nextInt(199999) + 1);
        car.setPrice(CarSize.COMPACT.getPrice());
        car.setRegistrationNumber(generateUniqueRegistrationNumber());
        car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase() + ".png");
        car.setRental(null);
        car.setReservations(null);

        return car;
    }

    public static Car generateEstatesClassCar() {
        Random random = new Random();
        Car car = new Car();

        car.setBrand("Skoda");
        car.setModel("Octavia Combi");
        car.setSize(CarSize.ESTATES);
        car.setTransmissionType(CarTransmissionType.values()[random.nextInt(CarTransmissionType.values().length)]);
        car.setProductionYear(random.nextInt(4) + 2020); // Random production year from 2020 to 2024
        car.setColor(CAR_COLORS[random.nextInt(CAR_COLORS.length)]);
        car.setMileage(random.nextInt(199999) + 1);
        car.setPrice(CarSize.ESTATES.getPrice());
        car.setRegistrationNumber(generateUniqueRegistrationNumber());
        car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase().replace(" ", "") + ".png");
        car.setRental(null);
        car.setReservations(null);

        return car;
    }

    public static Car generateSuvClassCar() {
        Random random = new Random();
        Car car = new Car();

        car.setBrand("Volvo");
        car.setModel("XC60");
        car.setSize(CarSize.SUV);
        car.setTransmissionType(CarTransmissionType.values()[random.nextInt(CarTransmissionType.values().length)]);
        car.setProductionYear(random.nextInt(4) + 2020); // Random production year from 2020 to 2024
        car.setColor(CAR_COLORS[random.nextInt(CAR_COLORS.length)]);
        car.setMileage(random.nextInt(199999) + 1);
        car.setPrice(CarSize.SUV.getPrice());
        car.setRegistrationNumber(generateUniqueRegistrationNumber());
        car.setImagePath(IMAGE_DIRECTORY + car.getBrand().toLowerCase() + "-" + car.getModel().toLowerCase().replace(" ", "") + ".png");
        car.setRental(null);
        car.setReservations(null);

        return car;
    }
}
