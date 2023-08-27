package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.CarStatus;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.repository.CarRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarService {

    CarRepository carRepository;

    @Transactional
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Transactional
    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }

    public List<Car> findAvailableCarsByConflictingReservations(List<Reservation> conflictingReservations) {

        List<Car> nonAvailableCars = conflictingReservations
                .stream()
                .map(Reservation::getCar)
                .distinct()
                .toList();

        return carRepository
                .findAll()
                .stream()
                .filter(car -> !nonAvailableCars.contains(car))
                .collect(Collectors.toList());
    }

    public List<Car> findCarsByColor(List<Car> cars, String color) {
        return cars
                .stream()
                .filter(car -> car.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    public List<Car> findCarsByColor(String color) {
        return carRepository.findCarsByColor(color);
    }

    public List<Car> findCarsByBrand(List<Car> cars, String brand) {
        return cars
                .stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }
    public List<Car> findCarsByBrand(String brand) {
        return carRepository.findCarsByBrand(brand);
    }

    public List<Car> findCarsByModel(List<Car> cars, String model) {
        return cars
                .stream()
                .filter(car -> car.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());
    }

    public List<Car> findCarsByModel(String model) {
        return carRepository.findCarsByModel(model);
    }
    public List<Car> findCarsByBodyType(List<Car> cars, String bodyType) {
        return cars
                .stream()
                .filter(car -> car.getBodyType().equalsIgnoreCase(bodyType))
                .collect(Collectors.toList());
    }

    public List<Car> findCarsByBodyType(String bodyType) {
        return carRepository.findCarsByBodyType(bodyType);
    }

    public List<Car> findCarsByPriceLessThanEqual(List<Car> cars, BigDecimal priceLimit) {
        return cars
                .stream()
                .filter(car -> car.getPrice().compareTo(priceLimit) <= 0)
                .collect(Collectors.toList());
    }

    public List<Car> findCarsByPriceLessThanEqual(BigDecimal priceLimit) {
        return carRepository.findCarsByPriceIsLessThanEqual(priceLimit);
    }

//    //TODO
//    public void updateCarStatusToRented(Long carId) {
//        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car id not found"));
//        car.setStatus(CarStatus.RENTED);
//        carRepository.save(car);
//    }
}
