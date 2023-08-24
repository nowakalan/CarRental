package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.repository.CarRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    CarRepository carRepository;

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }
}
