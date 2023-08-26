package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.CarRentRepository;
import pl.zdjavapol140.carrental.repository.CarRepository;
import pl.zdjavapol140.carrental.repository.BranchRepository;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

import java.time.LocalDate;

@Service
public class CarRentService {

    CarRentRepository carRentRepository;


    //TODO
    //Czy tak można stworzyć nowy wynajem?
    //Czy stworzyć opcjonalnt konstruktor bez notatki?
    //Czy dodać ew. pusty String?

    public void createCarRent(Reservation reservation, Employee employee) {
       CarRent carRent = new CarRent(reservation, LocalDate.now(),employee,null);
       carRentRepository.save(carRent);
    }

//    //TODO
//    @Transactional
//    public void addCarRent(CarRent carRent) {
//        carRentRepository.save(carRent);
//        Reservation reservation = reservationRepository.findById(carRent.getReservationId()).orElseThrow(() -> new RuntimeException("Reservation Id not foung"));
//        reservation.setStatus(ReservationStatus.IN_PROGRESS);
//        reservationRepository.save(reservation);
//
//        Car car = carRepository.findById(reservation.getCarId()).orElseThrow(() -> new RuntimeException("Car id not found"));
//        car.setStatus(CarStatus.RENTED);
//        carRepository.save(car);
//
//        Division division = divisionRepository.findById(reservation.getStartDivisionId()).orElseThrow(() -> new RuntimeException("Division id not found"));
//        division.getAvailableCars().remove(car);
//        divisionRepository.save(division);
//    }

    public void addCarRent(CarRent carRent) {
        carRentRepository.save(carRent);
    }

    public void deleteCarRent(Long id) {
        CarRent carRent = carRentRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
        carRentRepository.delete(carRent);
    }


}
