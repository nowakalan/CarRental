package pl.zdjavapol140.carrental.service;

import org.springframework.stereotype.Service;

import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.model.ReservationStatus;
import pl.zdjavapol140.carrental.repository.CarRepository;
import pl.zdjavapol140.carrental.repository.CustomerRepository;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public ReservationService(ReservationRepository reservationRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public List<Reservation> findReservationsByCarId(Long carId) {
        return reservationRepository.findReservationsByCarId(carId);
    }


    public List<Reservation> findReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findReservationsByStatusIs(status);
    }
//
//    public List<Reservation> findReservationsByStatusIsCompleted() {
//        return reservationRepository.findReservationsByStatusIs(ReservationStatus.COMPLETED);
//    }
//
//    public List<Reservation> findReservationsByStatusIsCanceled() {
//        return reservationRepository.findReservationsByStatusIs(ReservationStatus.CANCELED);
//    }

    List<Reservation> findConflictingReservations(LocalDateTime currentStartDateTime, LocalDateTime currentEndDateTime) {
        List<Reservation> conflictingReservations = new ArrayList<>();

        List<Reservation> conflictingStartDateTimeReservations = reservationRepository
                .findReservationsByStartDateTimeBetween(currentStartDateTime, currentEndDateTime);
        List<Reservation> conflictingEndDateTimeReservations = reservationRepository
                .findReservationsByEndDateTimeBetween(currentStartDateTime, currentEndDateTime);

        conflictingReservations.addAll(conflictingStartDateTimeReservations);
        conflictingReservations.addAll(conflictingEndDateTimeReservations);

        return conflictingReservations.stream().distinct().collect(Collectors.toList());

    }



    public List<Reservation> findReservationsByCustomerId(Long customerId) {
        return reservationRepository.findReservationsByCustomerId(customerId);
    }

    public boolean updateCustomer(Long reservationId, Long customerId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalReservation.isEmpty() || optionalCustomer.isEmpty())
            return false;
        Reservation reservationToUpdate = optionalReservation.get();
        Customer customer = optionalCustomer.get();
        reservationToUpdate.setCustomer(customer);
        //TODO
        //czy muszę zapisywać zmianę w Customer, czy hibernate zrobi to sam?
        customer.getReservations().add(reservationToUpdate);
        customerRepository.save(customer);
        //
        reservationRepository.save(reservationToUpdate);
        return true;
    }

//         reservationToUpdate.setCar(updatedReservation.getCar());
//         reservationToUpdate.setStartDateTime(updatedReservation.getStartDateTime());
//         reservationToUpdate.setEndDateTime(updatedReservation.getEndDateTime());

//    public List<Reservation> findReservationsByStartDateTimeAndEndDateTimeAreNotBetween(LocalDateTime start, LocalDateTime end) {
//        List<Car> carsAvailableBefore = reservationRepository.findReservationsByEndDateTimeIsBefore(start).stream().map(Reservation::getCar).distinct().toList();
//        List<Car> carsAvailableAfter = reservationRepository.findReservationsByStartDateTimeIsAfter(end);
//        return null;
//    }
//    public void addReservation(Reservation reservation) {
//        reservation.setStatus(ReservationStatus.SET);
//        reservationRepository.save(reservation);
//    }
//
//    //TODO
//    public Long updateReservationStatusToInProgress(Long reservationId) {
//        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation Id not foung"));
//        reservation.setStatus(ReservationStatus.IN_PROGRESS);
//        reservationRepository.save(reservation);
//        return reservation.getCarId();
//}
}
