package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.CarStatus;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.repository.CarRepository;

import java.util.List;

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

//    //TODO
//    public void updateCarStatusToRented(Long carId) {
//        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car id not found"));
//        car.setStatus(CarStatus.RENTED);
//        carRepository.save(car);
//    }
}
