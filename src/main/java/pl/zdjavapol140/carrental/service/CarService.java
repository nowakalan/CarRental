package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Car;
import lombok.extern.slf4j.Slf4j;
import pl.zdjavapol140.carrental.model.CarSize;
import pl.zdjavapol140.carrental.repository.CarRepository;
import pl.zdjavapol140.carrental.repository.ReservationRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CarService {

    private final CarRepository carRepository;



    public List<Car> getAll() {

        return carRepository.findAll();
    }

    private final ReservationRepository reservationRepository;

    public CarService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }


    @Transactional
    public boolean addCar(Car car) {
        carRepository.save(car);
        return true;
    }

    @Transactional
    public boolean removeCar(Long carId) {
        Optional<Car> optionalCarToRemove = carRepository.findById(carId);
        if (optionalCarToRemove.isEmpty()) {
            throw new RuntimeException("Car id not found");
        }
        carRepository.delete(optionalCarToRemove.get());
        return true;
    }


    public Car findCarById(Long carId) {
        return carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car id not found"));
    }
    public List<Car> findCarsBySize(CarSize size) {
        return carRepository.findCarsBySize(size);
    }

    public List<Car> findCarsBySize(List<Car> cars, CarSize size) {

        return cars
                .stream()
                .filter(car -> car.getSize().equals(size))
                .collect(Collectors.toList());
    }

    public List<Car> findCarsByPriceLessThanEqual(BigDecimal priceLimit) {

        return carRepository.findCarsByPriceIsLessThanEqual(priceLimit);
    }

    public List<Car> findCarsByPriceLessThanEqual(List<Car> cars, BigDecimal priceLimit) {

        return cars
                .stream()
                .filter(car -> car.getPrice().compareTo(priceLimit) <= 0)
                .collect(Collectors.toList());
    }




//    public List<Car> findCarsByBrand(List<Car> cars, String brand) {
//        return cars
//                .stream()
//                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
//                .collect(Collectors.toList());
//    }
//    public List<Car> findCarsByBrand(String brand) {
//        return carRepository.findCarsByBrand(brand);
//    }
//
//    public List<Car> findCarsByModel(List<Car> cars, String model) {
//        return cars
//                .stream()
//                .filter(car -> car.getModel().equalsIgnoreCase(model))
//                .collect(Collectors.toList());
//    }
//
//    public List<Car> findCarsByModel(String model) {
//        return carRepository.findCarsByModel(model);
//    }
//    public List<Car> findCarsByColor(List<Car> cars, String color) {
//        return cars
//                .stream()
//                .filter(car -> car.getColor().equalsIgnoreCase(color))
//                .collect(Collectors.toList());
//    }
//
//    public List<Car> findCarsByColor(String color) {
//        return carRepository.findCarsByColor(color);
//    }

//    //TODO
//    public void updateCarStatusToRented(Long carId) {
//        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car id not found"));
//        car.setStatus(CarStatus.RENTED);
//        carRepository.save(car);
//    }
}
