package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.model.ReservationStatus;
import pl.zdjavapol140.carrental.repository.BranchRepository;
import pl.zdjavapol140.carrental.repository.CarRepository;
import pl.zdjavapol140.carrental.repository.CustomerRepository;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final BranchRepository branchRepository;

    public ReservationService(ReservationRepository reservationRepository, CustomerRepository customerRepository, CarRepository carRepository, BranchRepository branchRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.branchRepository = branchRepository;
    }

    public Reservation findReservationById(Long id) throws RuntimeException {

        return reservationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation id not found"));
    }


    /**
     * creates new reservation (by allArgsConstructor)
     */

    public Reservation createReservation(Long carId,
                                         Long customerId,
                                         LocalDateTime pickUpDateTime,
                                         LocalDateTime dropOffDateTime,
                                         Long pickUpBranchId,
                                         Long dropOffBranchId) {

        return new Reservation(
                null,
                ReservationStatus.SET,
                LocalDateTime.now(),
                customerRepository.findById(customerId)
                        .orElseThrow(() -> new RuntimeException("Customer id not found")),
                carRepository.findById(carId)
                        .orElseThrow(() -> new RuntimeException("Car id not found")),
                pickUpDateTime,
                branchRepository.findById(pickUpBranchId)
                        .orElseThrow(() -> new RuntimeException("Branch id not found")).getId(),
                dropOffDateTime,
                branchRepository.findById(dropOffBranchId)
                        .orElseThrow(() -> new RuntimeException("Branch id not found")).getId(),
                null);
    }

    /**
     * saves reservation in db
     */
    @Transactional
    public boolean addReservation(Reservation reservation) throws RuntimeException {

        if (!findAvailableCars(reservation.getPickUpDateTime(),
                reservation.getDropOffDateTime(),
                reservation.getPickUpBranchId(),
                reservation.getDropOffBranchId())
                .contains(reservation.getCar())) {
            throw new RuntimeException("Car is unavailable");
        }
        reservationRepository.save(reservation);
        return true;
    }

    /**
     * deletes reservation from db
     */
    @Transactional
    public boolean deleteReservationById(Long reservationId) throws RuntimeException {

        reservationRepository.delete(findReservationById(reservationId));
        return true;
    }

    @Transactional
    public boolean updateReservationStatus(Long reservationId, ReservationStatus status) throws RuntimeException {

        Reservation reservation = findReservationById(reservationId);
        reservation.setStatus(status);
        reservationRepository.save(reservation);
        return true;
    }

    public List<Reservation> findAllReservations() {

        return reservationRepository.findAll();
    }


    /**
     * @return reservations (by rentalId)
     */

    public List<Reservation> findReservationsByRentalId(Long rentalId) {

        return carRepository
                .findCarsByRentalId(rentalId)
                .stream()
                .flatMap(car -> car.getReservations().stream())
                .toList();
    }

    /**
     * @return reservations (by customerId)
     */
    public List<Reservation> findReservationsByCustomerId(Long customerId) {

        return reservationRepository.findReservationsByCustomerId(customerId);
    }

    /**
     * @return reservations (by carId)
     */
    public List<Reservation> findReservationsByCarId(Long carId) {

        return reservationRepository.findReservationsByCarId(carId);
    }


    /**
     * @return reservations (by status)
     */
    public List<Reservation> findReservationsByStatus(ReservationStatus status) {

        return reservationRepository.findReservationsByStatus(status);
    }

    /**
     * @return availableCars (by pickUp/dropOff dateTime, pickUp/dropOff branchId)
     * The method searches for cars without reservations for conflicting term
     * and available in current place
     */

    public List<Car> findAvailableCars(LocalDateTime currentPickUpDateTime,
                                       LocalDateTime currentDropOffDateTime,
                                       Long currentPickUpBranchId,
                                       Long currentDropOffBranchId) {

        List<Car> cars = carRepository.findAll();

        cars.removeAll(reservationRepository
                .findUnavailableCars(currentPickUpDateTime, currentDropOffDateTime));

        List<Car> carsToRemove = new ArrayList<>();

        for (Car car : cars) {
            if (car.getReservations().isEmpty()) {
                continue;
            }

            Optional<Reservation> previousReservation =
                    findPreviousReservationBefore(car.getId(), currentPickUpDateTime);

            Optional<Reservation> nextReservation =
                    findNextReservationAfter(car.getId(), currentDropOffDateTime);

            if (previousReservation.isPresent()
                && !previousReservation.get().getDropOffBranchId().equals(currentPickUpBranchId)) {

                carsToRemove.add(car);
                continue;
            }

            if (nextReservation.isPresent()
                && !nextReservation.get().getPickUpBranchId().equals(currentDropOffBranchId)) {

                carsToRemove.add(car);
            }
        }
        cars.removeAll(carsToRemove);
        return cars;
    }

    /**
     * @return optional previous reservation (by carId, currentPickUpDateTime)
     */

    public Optional<Reservation> findPreviousReservationBefore(Long carId, LocalDateTime currentPickUpDateTime) {

        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car id not found"));

        return car.getReservations()
                .stream()
                .filter(reservation -> reservation != null
                                       && !reservation.getStatus().equals(ReservationStatus.CANCELED)
                                       && reservation.getDropOffDateTime().isBefore(currentPickUpDateTime))
                .max(Comparator.comparing(Reservation::getDropOffDateTime));
    }

    /**
     * @return optional next reservation (by carId, currentPickUpDateTime)
     */

    public Optional<Reservation> findNextReservationAfter(Long carId, LocalDateTime currentDropOffDateTime) {

        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car id not found"));

        return car.getReservations()
                .stream()
                .filter(reservation -> reservation != null
                                       && !reservation.getStatus().equals(ReservationStatus.CANCELED)
                                       && reservation.getPickUpDateTime().isAfter(currentDropOffDateTime))
                .min(Comparator.comparing(Reservation::getPickUpDateTime));
    }

    //TODO kiedyś: Optymalizacja wykorzystania floty: samochód jak najkrócej stoi bezczynnie; ograniczony czasowo status samochodu UNAVAILABLE w związku z przeglądem/naprawą
}
