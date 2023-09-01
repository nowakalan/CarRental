package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.CarSize;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.repository.CarRepository;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CarService {

    CarRepository carRepository;
    ReservationRepository reservationRepository;

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

    public List<Car> findCarsByPriceLessThanEqual(List<Car> cars, BigDecimal priceLimit) {
        return cars
                .stream()
                .filter(car -> car.getPrice().compareTo(priceLimit) <= 0)
                .collect(Collectors.toList());
    }

    public List<Car> findCarsByPriceLessThanEqual(BigDecimal priceLimit) {
        return carRepository.findCarsByPriceIsLessThanEqual(priceLimit);
    }


}
