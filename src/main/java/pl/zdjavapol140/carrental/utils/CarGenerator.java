package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.CarBodyType;
import pl.zdjavapol140.carrental.model.CarTransmissionType;

import java.math.BigDecimal;
import java.util.Random;

public class CarGenerator {

    private static final String[] CAR_BRANDS = {"Toyota", "Honda", "Ford", "Volkswagen", "Chevrolet", "BMW"};
    private static final String[] CAR_MODELS = {"Camry", "Civic", "Focus", "Jetta", "Malibu", "X5"};
    private static final String[] CAR_COLORS = {"Red", "Blue", "Silver", "Black", "White", "Gray"};
    private static final double[] CAR_MILEAGES = {10000.0, 20000.0, 30000.0, 40000.0, 50000.0};
    private static final BigDecimal[] CAR_PRICES = {BigDecimal.valueOf(50), BigDecimal.valueOf(60), BigDecimal.valueOf(70), BigDecimal.valueOf(80), BigDecimal.valueOf(90)};

    public static Car generateRandomCar() {
        Random random = new Random();
        Car car = new Car();

        car.setBrand(CAR_BRANDS[random.nextInt(CAR_BRANDS.length)]);
        car.setModel(CAR_MODELS[random.nextInt(CAR_MODELS.length)]);
        car.setBodyType(CarBodyType.values()[random.nextInt(CarBodyType.values().length)]);
        car.setTransmissionType(CarTransmissionType.values()[random.nextInt(CarTransmissionType.values().length)]);
        car.setProductionYear(random.nextInt(10) + 2010); // Losowe lata produkcji od 2010 do 2019
        car.setColor(CAR_COLORS[random.nextInt(CAR_COLORS.length)]);
        car.setMileage(CAR_MILEAGES[random.nextInt(CAR_MILEAGES.length)]);
        car.setPrice(CAR_PRICES[random.nextInt(CAR_PRICES.length)]);
        car.setRental(null);
        car.setReservations(null);

        return car;
    }

}
