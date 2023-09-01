package pl.zdjavapol140.carrental.service;

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

    /**
     *
     * @param carId
     * @param customerId
     * @param pickUpDateTime
     * @param dropOffDateTime
     * @param pickUpBranchId
     * @param dropOffBranchId
     */

    public void createReservation(Long carId, Long customerId, LocalDateTime pickUpDateTime, LocalDateTime dropOffDateTime, Long pickUpBranchId, Long dropOffBranchId) {
        Reservation reservation = new Reservation();
        reservation.setStatus(ReservationStatus.SET);
        reservation.setBookingDate(LocalDateTime.now());
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        reservation.setCustomer(customer);
        Car car = carRepository.findCarById(carId);
        reservation.setCar(car);
        reservation.setPickUpDateTime(pickUpDateTime);
        reservation.setPickUpBranchId(pickUpBranchId);
        reservation.setDropOffDateTime(dropOffDateTime);
        reservation.setDropOffBranchId(dropOffBranchId);
    }

    /**
     * @return availableCars (by pickUp/dropOff dateTime, pickUp/dropOff branchId)
     * The method searches for cars without reservations for conflicting term
     *      and available in current place
     */

    public List<Car> findAvailableCars(@Param("currentPickUpDateTime")LocalDateTime currentPickUpDateTime,
                                       @Param("currentDropOffDateTime")LocalDateTime currentDropOffDateTime,
                                       @Param("currentPickUpBranchId")Long currentPickUpBranchId,
                                       @Param("currentDropOffBranchId")Long currentDropOffBranchId) {

        List<Car> cars = carRepository.findAll();
        cars.removeAll(reservationRepository.findUnavailableCars(currentPickUpDateTime, currentDropOffDateTime));

        for (Car car : cars) {
            Optional<Reservation> previousReservation = findPreviousReservationBefore(car.getId(), currentPickUpDateTime);
            Optional<Reservation> nextReservation = findNextReservationAfter(car.getId(), currentDropOffDateTime);

            if ((previousReservation.isPresent() && !previousReservation.get().getDropOffBranchId().equals(currentPickUpBranchId))
                    || (nextReservation.isPresent() && !nextReservation.get().getPickUpBranchId().equals(currentDropOffBranchId))) {
                cars.remove(car);
            }
        }
        return cars;
    }


    /**
     * @return optional previous reservation (by carId, currentPickUpDateTime)
     */

    public Optional<Reservation> findPreviousReservationBefore(@Param("carId")Long carId, @Param("currentPickUpDateTime")LocalDateTime currentPickUpDateTime) {
        Car car = carRepository.findCarById(carId);
        return car.getReservations()
                .stream()
                .filter(reservation -> reservation.getDropOffDateTime().isBefore(currentPickUpDateTime))
                .max(Comparator.comparing(Reservation::getDropOffDateTime));
    }

    /**
     * @return optional next reservation (by carId, currentPickUpDateTime)
     */
    public Optional<Reservation> findNextReservationAfter(@Param("carId")Long carId, @Param("currentDropOffUpDateTime")LocalDateTime currentDropOffDateTime) {
        Car car = carRepository.findCarById(carId);
        return car.getReservations()
                .stream()
                .filter(reservation -> reservation.getPickUpDateTime().isAfter(currentDropOffDateTime))
                .min(Comparator.comparing(Reservation::getPickUpDateTime));
    }

    /**
     * @return reservations (by rentalId)
     */

    public List<Reservation> findReservationsByRentalId(@Param("rentalId")Long rentalId) {
        return carRepository
                .findCarsByRentalId(rentalId)
                .stream()
                .flatMap(car -> car.getReservations().stream())
                .toList();
    }

    /**
     * @return reservations (by customerId)
     */
    public List<Reservation> findReservationsByCustomerId(@Param("customerId") Long customerId) {
        return reservationRepository.findReservationsByCustomerId(customerId);
    }

    /**
     * @return reservations (by carId)
     */
    public List<Reservation> findReservationsByCarId(@Param("carId")Long carId) {
        return reservationRepository.findReservationsByCarId(carId);
    }


    /**
     * @return reservations (by status)
     */
    public List<Reservation> findReservationsByStatus(@Param("status") ReservationStatus status) {
        return reservationRepository.findReservationsByStatusIs(status);
    }

        /*
    Metoda zmienia branch w obiekcie car, jeśli status rezerwacji jest in_progress, a branch zwrotu jest inny niż odbioru
     */
//    public void setCarBranchByReservations(Long carId, LocalDateTime currentDateTime) {
//        reservationRepository.findReservationsByCarIdAndDropOffDateTimeBefore(carId, currentDateTime)
//                .stream()
//                .max(Comparator.comparing(Reservation::getDropOffDateTime))
//                .


//        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Id not found"));
//        Reservation reservation = car.getReservations().stream().filter(r -> r.getStatus().equals(ReservationStatus.IN_PROGRESS)).findFirst().orElseThrow(() -> new RuntimeException("No reservation in progress"));
//        if (!reservation.getDropOffBranchId().equals(reservation.getPickUpBranchId())) {
//            car.setBranch(branchRepository.getReferenceById(reservation.getDropOffBranchId()));
//            carRepository.save(car);
//        }


//
//    public List<Reservation> findReservationsByStatusIsCompleted() {
//        return reservationRepository.findReservationsByStatusIs(ReservationStatus.COMPLETED);
//    }
//
//    public List<Reservation> findReservationsByStatusIsCanceled() {
//        return reservationRepository.findReservationsByStatusIs(ReservationStatus.CANCELED);
//    }

    /* The method searches for reservations colliding with the time period sought
     * (beginning or ending between the search dates
     * and beginning before and ending after the search term).
     */
//    public List<Reservation> findConflictingReservations(LocalDateTime currentPickUpDateTime, LocalDateTime currentDropOffDateTime) {
//        List<Reservation> reservationsConflictingByDateTime = new ArrayList<>();
//
//        List<Reservation> reservationsConflictingByPickUpDateTime = reservationRepository
//                .findReservationsByPickUpDateTimeBetween(currentPickUpDateTime, currentDropOffDateTime);
//
//        List<Reservation> reservationsConflictingByDropOffDateTime = reservationRepository
//                .findReservationsByDropOffDateTimeBetween(currentPickUpDateTime, currentDropOffDateTime);
//
//        List<Reservation> reservationsConflictingByPickUpAndDropOffDateTime = reservationRepository
//                .findReservationsByPickUpDateTimeBefore(currentPickUpDateTime)
//                .stream()
//                .filter(reservation -> reservationRepository.findReservationsByDropOffDateTimeAfter(currentDropOffDateTime).contains(reservation))
//                .toList();
//
//        reservationsConflictingByDateTime.addAll(reservationsConflictingByPickUpDateTime);
//        reservationsConflictingByDateTime.addAll(reservationsConflictingByDropOffDateTime);
//        reservationsConflictingByDateTime.addAll(reservationsConflictingByPickUpAndDropOffDateTime);
//
//        return reservationsConflictingByDateTime.stream().distinct().collect(Collectors.toList());
//
//    }

    public List<Car> findConflictingReservations(LocalDateTime currentPickUpDateTime, LocalDateTime currentDropOffDateTime) {
        return reservationRepository.findUnavailableCars(currentPickUpDateTime, currentDropOffDateTime);
    }




//    public boolean updateCustomer(Long reservationId, Long customerId) {
//        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
//        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//        if (optionalReservation.isEmpty() || optionalCustomer.isEmpty()) {
//            return false;
//        }
//        Reservation reservationToUpdate = optionalReservation.get();
//        Customer customer = optionalCustomer.get();
//        reservationToUpdate.setCustomer(customer);
//        reservationRepository.save(reservationToUpdate);
//        return true;
//    }

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
