package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.BranchRepository;
import pl.zdjavapol140.carrental.repository.CarRepository;
import pl.zdjavapol140.carrental.repository.CustomerRepository;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


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

    public Reservation createNewReservation(Long carId,
                                            Long customerId,
                                            LocalDateTime pickUpDateTime,
                                            LocalDateTime dropOffDateTime,
                                            Long pickUpBranchId,
                                            Long dropOffBranchId) {

        Reservation reservation = new Reservation(
                null,
                ReservationStatus.SET,
                null,
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

        reservation.setTotalPrice(this.calculateReservationPrice(reservation));

        return reservation;
    }


    /**
     * saves reservation in db
     */

    //TODO co z obsługą błędów? Czy warto wyrzucać wyjątek z metody/przekazywać wyżej?
    @Transactional
    public void addReservation(Reservation reservation) {

        if (!findAvailableCarsWithOptionalAdjacentReservations(reservation.getPickUpDateTime(),
                reservation.getDropOffDateTime(),
                reservation.getPickUpBranchId(),
                reservation.getDropOffBranchId())
                .containsKey(reservation.getCar())) {
            throw new RuntimeException("Car is unavailable");
        }
        reservation.setBookingDate(LocalDateTime.now());

        reservationRepository.save(reservation);
    }

    /**
     * deletes reservation from db
     */
    @Transactional
    public void deleteReservationById(Long reservationId) {

        reservationRepository.delete(findReservationById(reservationId));
    }

    @Transactional
    public void updateReservation(Reservation reservationToUpdate) {

        this.findReservationById(reservationToUpdate.getId());
        reservationRepository.save(reservationToUpdate);
    }

    @Transactional
    public boolean updateReservationStatus(Long reservationId, ReservationStatus status) {

        Reservation reservation = findReservationById(reservationId);
        reservation.setStatus(status);
        reservationRepository.save(reservation);
        return true;
    }

    @Transactional
    public boolean updateReservationPickUpDateTime(Long reservationId, LocalDateTime newPickUpDateTime) {

        Reservation reservation = findReservationById(reservationId);
        reservation.setPickUpDateTime(newPickUpDateTime);
        reservationRepository.save(reservation);
        return true;
    }

    @Transactional
    public boolean updateReservationSDropOffDateTime(Long reservationId, LocalDateTime newDropOffDateTime) {

        Reservation reservation = findReservationById(reservationId);
        reservation.setDropOffDateTime(newDropOffDateTime);
        reservationRepository.save(reservation);
        return true;
    }

    @Transactional
    public boolean updateReservationPickUpBranchId(Long reservationId, Long newPickUpBranchId) {

        Reservation reservation = findReservationById(reservationId);
        reservation.setPickUpBranchId(newPickUpBranchId);
        reservationRepository.save(reservation);
        return true;
    }

    @Transactional
    public boolean updateReservationDropOffBranchId(Long reservationId, Long newDropOffBranchId) {

        Reservation reservation = findReservationById(reservationId);
        reservation.setDropOffBranchId(newDropOffBranchId);
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
    public List<Reservation> findReservationsByStatusEquals(ReservationStatus status) {

        return reservationRepository.findReservationsByStatusEquals(status);
    }

    public List<Reservation> findReservationsByStatusNot(ReservationStatus status) {

        return reservationRepository.findReservationsByStatusNot(status);
    }

    /**
     * @return reservations (by pickUpBranchId)
     */
    public List<Reservation> findReservationsByPickUpBranchId(Long branchId) {

        return reservationRepository.findReservationsByPickUpBranchId(branchId);
    }

    /**
     * @return reservations (by dropOffBranchId)
     */
    public List<Reservation> findReservationsByDropOffBranchId(Long branchId) {

        return reservationRepository.findReservationsByDropOffBranchId(branchId);
    }

    /**
     * @return reservations (by pickUpDateTime between)
     */
    public List<Reservation> findReservationsByPickUpDateTimeBetween(LocalDateTime minDateTime, LocalDateTime maxDateTime) {

        return reservationRepository.findReservationsByPickUpDateTimeBetween(minDateTime, maxDateTime);
    }

    /**
     * @return reservations (by dropOffDateTime between)
     */
    public List<Reservation> findReservationsByDropOffDateTimeBetween(LocalDateTime minDateTime, LocalDateTime maxDateTime) {

        return reservationRepository.findReservationsByDropOffDateTimeBetween(minDateTime, maxDateTime);
    }

    /**
     * @return reservations (by bookingDate between)
     */
    public List<Reservation> findReservationsByBookingDateBetween(LocalDateTime minDate, LocalDateTime maxDate) {

        return reservationRepository.findReservationsByBookingDateBetween(minDate, maxDate);
    }

    public List<Reservation> findReservationsByCarSize(CarSize size) {

        return reservationRepository.findReservationsByCarSize(size);
    }

    public List<Reservation> findReservationsByCarTransmissionType(CarTransmissionType transmissionType) {

        return reservationRepository.findReservationsByCar_TransmissionType(transmissionType);
    }

    /**
     * @return availableCars (by pickUp/dropOff dateTime, pickUp/dropOff branchId)
     * The method searches for cars without reservations for conflicting term
     * and available in current place
     */

    //TODO Przemyśleć cancelled, ustawić czasy na transfer
    public Map<Car, List<Optional<Reservation>>> findAvailableCarsWithOptionalAdjacentReservations(LocalDateTime currentPickUpDateTime,
                                                                                                   LocalDateTime currentDropOffDateTime,
                                                                                                   Long currentPickUpBranchId,
                                                                                                   Long currentDropOffBranchId) {
        if (currentPickUpDateTime.isAfter(currentDropOffDateTime)) {

            throw new RuntimeException("The drop-off date and time must be later than the pickup date and time");
        }

        if (branchRepository.findById(currentPickUpBranchId).isEmpty() || branchRepository.findById(currentDropOffBranchId).isEmpty()) {

            throw new RuntimeException("Branch id not found");
        }

        List<Car> cars = carRepository.findAll();

        Map<Car, List<Optional<Reservation>>> availableCarsWithOptionalAdjacentReservations = new HashMap<>();

        cars.removeAll(reservationRepository
                .findUnavailableCars(currentPickUpDateTime, currentDropOffDateTime));

        if (cars.isEmpty()) {

            throw new RuntimeException("Available cars not found");
        }

        for (Car car : cars) {

            List<Optional<Reservation>> optionalAdjacentReservations = new LinkedList<>();

            Optional<Reservation> optionalPreviousReservation = this.findNotCancelledPreviousReservationBefore(car.getId(), currentPickUpDateTime);

            if (this.isPreviousReservationEmptyOrNotConflicting(optionalPreviousReservation, currentPickUpBranchId, currentPickUpDateTime)) {

                optionalAdjacentReservations.add(optionalPreviousReservation);

            } else {

                continue;
            }

            Optional<Reservation> optionalNextReservation = this.findNotCancelledNextReservationAfter(car.getId(), currentDropOffDateTime);

            if (this.isNextReservationEmptyOrNotConflicting(optionalNextReservation, currentDropOffBranchId, currentDropOffDateTime)) {

                optionalAdjacentReservations.add(optionalNextReservation);

            } else {

                continue;
            }

            availableCarsWithOptionalAdjacentReservations.put(car, optionalAdjacentReservations);

        }

        return availableCarsWithOptionalAdjacentReservations;

    }

    public List<Car>findAvailableCars(Map<Car, List<Optional<Reservation>>> carsWithOptionalAdjacentReservations) {

        return new ArrayList<>(carsWithOptionalAdjacentReservations.keySet());
    }

    private boolean isReservationPreviousToCurrent(Reservation reservation, Reservation currentReservation) {

        return reservation.getDropOffDateTime().isBefore(currentReservation.getPickUpDateTime());
    }

    private boolean isReservationNextToCurrent(Reservation reservation, Reservation currentReservation) {

        return reservation.getPickUpDateTime().isAfter(currentReservation.getDropOffDateTime());
    }

    private boolean isPreviousReservationEmptyOrNotConflicting(Optional<Reservation> optionalPreviousReservation, Long currentPickUpBranchId, LocalDateTime currentPickUpDateTime) {

        return isPreviousReservationDropOffLocationEqualCurrentPickUpLocation(optionalPreviousReservation, currentPickUpBranchId) ||
                isEnoughTimeForTransferBetweenPreviousDropOffDateTimeAndCurrentPickUpDateTime(optionalPreviousReservation, currentPickUpDateTime);
    }


    private boolean isNextReservationEmptyOrNotConflicting(Optional<Reservation> optionalNextReservation, Long currentDropOffBranchId, LocalDateTime currentDropOffDateTime) {

        return isNextPickUpLocationEqualCurrentDropOffLocation(optionalNextReservation, currentDropOffBranchId) ||
                isEnoughTimeForTransferBetweenCurrentDropOffDateTimeAndNextPickUpDateTime(optionalNextReservation, currentDropOffDateTime);
    }

    private boolean isPreviousReservationDropOffLocationEqualCurrentPickUpLocation(Optional<Reservation> optionalPreviousReservation, Long currentPickUpBranchId) {

        return optionalPreviousReservation.isEmpty() || optionalPreviousReservation.get().getDropOffBranchId().equals(currentPickUpBranchId);
    }

    private boolean isEnoughTimeForTransferBetweenPreviousDropOffDateTimeAndCurrentPickUpDateTime(Optional<Reservation> optionalPreviousReservation, LocalDateTime currentPickUpDateTime) {

        return optionalPreviousReservation.isEmpty() || !optionalPreviousReservation.get().getDropOffDateTime().plusHours(23L).isAfter(currentPickUpDateTime);
    }

    private boolean isNextPickUpLocationEqualCurrentDropOffLocation(Optional<Reservation> optionalNextReservation, Long currentDropOffBranchId) {

        return optionalNextReservation.isEmpty() || optionalNextReservation.get().getPickUpBranchId().equals(currentDropOffBranchId);
    }

    private boolean isEnoughTimeForTransferBetweenCurrentDropOffDateTimeAndNextPickUpDateTime(Optional<Reservation> optionalNextReservation, LocalDateTime currentDropOffDateTime) {

        return optionalNextReservation.isEmpty() || !optionalNextReservation.get().getPickUpDateTime().minusHours(23L).isBefore(currentDropOffDateTime);
    }

    //TODO ustawić wartość surcharge
    public BigDecimal calculateReservationPrice(Reservation reservation) {

        long durationInDaysRoundedCeil = this.calculateReservationDurationInDaysRoundedCeil(reservation);

        BigDecimal surcharge = reservation.getCar().getPrice().multiply(BigDecimal.valueOf(0.5));

        if (!reservation.getDropOffBranchId().equals(reservation.getPickUpBranchId())) {
            surcharge = BigDecimal.ONE;
        }
        return reservation.getCar().getPrice().multiply(BigDecimal.valueOf(durationInDaysRoundedCeil)).add(surcharge);
    }

    /**
     *
    Oblicza czas trwania rezerwacji w rozpoczętych dniach
     */
    private long calculateReservationDurationInDaysRoundedCeil(Reservation reservation) {

        Duration duration = Duration.between(reservation.getPickUpDateTime(), reservation.getDropOffDateTime());

        long days = duration.toDays();

        long minutes = duration.toMinutes();

        if (minutes - Duration.ofDays(days).toMinutes() > 1) {
            days++;
        }
        return days;
    }


    /**
     * Kiedy status poprzedniej rezerwacji jest: completed/set/in_progress/service i nie zgadzają się lokacje
     */
    private Reservation setTransferReservationBeforeCurrent(Reservation previousReservation, Reservation currentReservation) {

        Reservation newTransferReservation = new Reservation(
                null,
                ReservationStatus.TRANSFER,
                LocalDateTime.now(),
                currentReservation.getCustomer(),
                currentReservation.getCar(),
                currentReservation.getPickUpDateTime().minusHours(8L),
                previousReservation.getPickUpBranchId(),
                currentReservation.getPickUpDateTime().minusHours(2L),
                currentReservation.getPickUpBranchId(),
                BigDecimal.ZERO);


        this.addReservation(newTransferReservation);
        return newTransferReservation;

    }

    /**
     * Kiedy status następnej rezerwacji jest: set/service i nie zgadzają się lokacje
     */
    private Reservation setTransferReservationAfterCurrent(Reservation nextReservation, Reservation currentReservation) {
        Reservation newTransferReservation = new Reservation(
                null,
                ReservationStatus.TRANSFER,
                LocalDateTime.now(),
                currentReservation.getCustomer(),
                currentReservation.getCar(),
                nextReservation.getPickUpDateTime().minusHours(8L),
                currentReservation.getDropOffBranchId(),
                nextReservation.getPickUpDateTime().minusHours(2L),
                nextReservation.getPickUpBranchId(),
                BigDecimal.ZERO);

        this.addReservation(newTransferReservation);
        return newTransferReservation;
    }



    /**
     * Kiedy status poprzedniej rezerwacji jest transfer i nie zgadzają się lokacje
     */

    private Reservation updateTransferReservationPreviousToCurrent(Reservation previousTransferReservation, Reservation currentReservation) {

        previousTransferReservation.setDropOffBranchId(currentReservation.getPickUpBranchId());

        previousTransferReservation.setPickUpDateTime(currentReservation.getPickUpDateTime().minusHours(8L));

        previousTransferReservation.setDropOffDateTime(currentReservation.getPickUpDateTime().minusHours(2L));

        reservationRepository.save(previousTransferReservation);
        return previousTransferReservation;

    }

    /**
     * Kiedy status następnej rezerwacji jest transfer i nie zgadzają się lokacje
     */

    private Reservation updateTransferReservationNextToCurrent(Reservation nextTransferReservation, Reservation currentReservation) {

        nextTransferReservation.setPickUpBranchId(currentReservation.getDropOffBranchId());

        reservationRepository.save(nextTransferReservation);
        return nextTransferReservation;

    }

    /**
     * Utworzenie wstępnej rezerwacji, bez zapisywania w DB
     */
    public Reservation createCurrentPreReservation(
            Car car,
            Customer customer,
            LocalDateTime currentPickUpDateTime,
            LocalDateTime currentDropOffDateTime,
            Long currentPickUpBranchId,
            Long currentDropOffBranchId) {


        Reservation currentPreReservation = new Reservation(
                null,
                ReservationStatus.SET,
                null,
                customer,
                car,
                currentPickUpDateTime,
                currentPickUpBranchId,
                currentDropOffDateTime,
                currentDropOffBranchId,
                null);

        currentPreReservation.setTotalPrice(
                this.calculateReservationPrice(currentPreReservation));

        return currentPreReservation;
    }

    /**
     *
     Creates map with available cars as keys and list of optional reservations adjacent to current as values
     */

    private Map<Car, List<Optional<Reservation>>> createMapWithAvailableCarsWithOptionalAdjacentReservations(Reservation currentPreReservation) {

        Map<Car, List<Optional<Reservation>>> availableCarsWithOptionalAdjacentReservations = this.findAvailableCarsWithOptionalAdjacentReservations(
                currentPreReservation.getPickUpDateTime(),
                currentPreReservation.getDropOffDateTime(),
                currentPreReservation.getPickUpBranchId(),
                currentPreReservation.getDropOffBranchId());

        this.checkIfPreReservedCarIsStillAvailable(availableCarsWithOptionalAdjacentReservations, currentPreReservation);

        return availableCarsWithOptionalAdjacentReservations;
    }

    /**
     *
    Checks if pre-reserved car is still available
     */
    private void checkIfPreReservedCarIsStillAvailable(Map<Car, List<Optional<Reservation>>> availableCarsWithOptionalAdjacentReservations, Reservation currentPreReservation) throws RuntimeException {

        Optional<Car> optionalCar = carRepository.findById(currentPreReservation.getCar().getId());

        if (optionalCar.isEmpty()) {

            throw new RuntimeException("Car id not found");
        }

        if (availableCarsWithOptionalAdjacentReservations.isEmpty()) {

            throw new RuntimeException("There are not available cars for current time and location");
        }

        if (!availableCarsWithOptionalAdjacentReservations.containsKey(optionalCar.get())) {

            throw new RuntimeException("Car is not available");
        }
    }

    /**
     *
     Updates, creates or deletes transfer reservation before current if needed
     */
    private Optional<Reservation> setOrUpdateOrDeletePreviousTransferReservation(Reservation currentPreReservation,
                                                                       Map<Car, List<Optional<Reservation>>> availableCarsWithOptionalAdjacentReservations) {

        Optional<Reservation> optionalNewTransferReservationPreviousToCurrent = Optional.empty();

        Optional<Reservation> optionalPreviousReservation = availableCarsWithOptionalAdjacentReservations.get(currentPreReservation.getCar()).get(0);

        if (optionalPreviousReservation.isPresent()) {

            Reservation previousReservation = optionalPreviousReservation.get();

            switch (previousReservation.getStatus()) {

                case TRANSFER -> {

                    if (previousReservation.getPickUpBranchId().equals(currentPreReservation.getPickUpBranchId())) {

                        this.deleteReservationById(previousReservation.getId());

                    } else {

                        optionalNewTransferReservationPreviousToCurrent = Optional.of(this.updateTransferReservationPreviousToCurrent(previousReservation, currentPreReservation));
                    }
                }

                case COMPLETED, IN_PROGRESS, SET, SERVICE -> {

                    if (!previousReservation.getDropOffBranchId().equals(currentPreReservation.getPickUpBranchId())) {

                        optionalNewTransferReservationPreviousToCurrent = Optional.of(this.setTransferReservationBeforeCurrent(previousReservation, currentPreReservation));
                    }
                }
                default -> throw new IllegalArgumentException("Previous reservation status can't be \"canceled\"");

            }
        }

        return optionalNewTransferReservationPreviousToCurrent;
    }

    /**
     *
     Updates, creates or deletes transfer reservation after current if needed
     */
    private Optional<Reservation> setOrUpdateOrDeleteNextTransferReservation(Reservation currentPreReservation,
                                                                   Map<Car, List<Optional<Reservation>>> availableCarsWithOptionalAdjacentReservations) {

        Optional<Reservation> optionalNextReservation = availableCarsWithOptionalAdjacentReservations.get(currentPreReservation.getCar()).get(1);

        Optional<Reservation> optionalNewTransferReservationNextToCurrent = Optional.empty();

        if (optionalNextReservation.isPresent()) {

            Reservation nextReservation = optionalNextReservation.get();


            switch (nextReservation.getStatus()) {

                case TRANSFER -> {

                    if (nextReservation.getDropOffBranchId().equals(currentPreReservation.getDropOffBranchId())) {

                        this.deleteReservationById(nextReservation.getId());

                    } else {

                        optionalNewTransferReservationNextToCurrent = Optional.of(this.updateTransferReservationNextToCurrent(nextReservation, currentPreReservation));
                    }
                }
                case SET, SERVICE -> {

                    if (!nextReservation.getPickUpBranchId().equals(currentPreReservation.getDropOffBranchId())) {

                        optionalNewTransferReservationNextToCurrent = Optional.of(this.setTransferReservationAfterCurrent(nextReservation, currentPreReservation));
                    }
                }
                default ->
                        throw new IllegalArgumentException("Next reservation status can't be canceled, completed or in progress");
            }
        }
        return optionalNewTransferReservationNextToCurrent;
    }

    /**
     *
     Sets current reservation if still available and updates, creates or deletes transfer reservation/reservations if needed
     */

    public List<Optional<Reservation>> setCurrentReservationAndOptionalTransferReservations(Reservation currentPreReservation) {

        List<Optional<Reservation>> reservations = new LinkedList<>();

        Map<Car, List<Optional<Reservation>>> availableCarsWithOptionalAdjacentReservations = this.createMapWithAvailableCarsWithOptionalAdjacentReservations(currentPreReservation);


        reservations.add(this.setOrUpdateOrDeletePreviousTransferReservation(currentPreReservation, availableCarsWithOptionalAdjacentReservations));


        reservations.add(this.setOrUpdateOrDeleteNextTransferReservation(currentPreReservation, availableCarsWithOptionalAdjacentReservations));


        this.addReservation(currentPreReservation);

        reservations.add(reservationRepository.findById(currentPreReservation.getId()));

        return reservations;

    }



    /**
     * @return optional previous reservation (by carId, currentPickUpDateTime)
     */

    private Optional<Reservation> findNotCancelledPreviousReservationBefore(Long carId, LocalDateTime currentPickUpDateTime) {

        return reservationRepository.findReservationsByCarId(carId)
                .stream()
                .filter(reservation -> reservation != null
                        && !reservation.getStatus().equals(ReservationStatus.CANCELLED)
                        && reservation.getDropOffDateTime().isBefore(currentPickUpDateTime))
                .max(Comparator.comparing(Reservation::getDropOffDateTime));
    }

    /**
     * @return optional next reservation (by carId, currentPickUpDateTime)
     */

    private Optional<Reservation> findNotCancelledNextReservationAfter(Long carId, LocalDateTime currentDropOffDateTime) {

        return reservationRepository.findReservationsByCarId(carId)
                .stream()
                .filter(reservation -> reservation != null
                        && !reservation.getStatus().equals(ReservationStatus.CANCELLED)
                        && reservation.getPickUpDateTime().isAfter(currentDropOffDateTime))
                .min(Comparator.comparing(Reservation::getPickUpDateTime));
    }

    //TODO może kiedyś: Optymalizacja wykorzystania floty; Samochód jak najkrócej stoi bezczynnie; Ograniczony czasowo status samochodu UNAVAILABLE w związku z przeglądem/naprawą

//    //TODO cancelled
//    public List<Car> findCarsAvailableForCurrentPickUpBranchAndDateTime(LocalDateTime currentPickUpDateTime, LocalDateTime currentDropOffDateTime, Long currentPickUpBranchId, Long currentDropOffBranchId) {
//
//        List<Car> carsAvailableForCurrentPickUpBranchAndDateTime = new ArrayList<>();
//
//        List<Car> cars = carRepository.findAll();
//        cars.removeAll(reservationRepository.findUnavailableCars(currentPickUpDateTime, currentDropOffDateTime));
//
//        for (Car car : cars) {
//            Optional<Reservation> previousReservation = this.findActivePreviousReservationBefore(car.getId(), currentPickUpDateTime);
//
//            if (previousReservation.isPresent() && previousReservation.get().getDropOffBranchId().equals(currentPickUpBranchId)) {
//                carsAvailableForCurrentPickUpBranchAndDateTime.add(car);
//            }
//        }
//        return carsAvailableForCurrentPickUpBranchAndDateTime;
//    }

//    public List<Car> findArrivalsByBranchIdAndDateTime(Long branchId, LocalDateTime currentDateTime) {
//
//        return reservationRepository.findReservationsByStatusNot(ReservationStatus.CANCELLED)
//                .stream()
//                .filter(
//                        reservation -> reservation.getDropOffBranchId().equals(branchId) &&
//                                       reservation.getDropOffDateTime().equals(currentDateTime)
//                )
//                .map(Reservation::getCar)
//                .collect(Collectors.toList());
//    }
//
//
//    public List<Car> findDeparturesByBranchIdAndDateTime(Long branchId, LocalDateTime currentDateTime) {
//
//        return reservationRepository.findReservationsByStatusNot(ReservationStatus.CANCELLED)
//                .stream()
//                .filter(
//                        reservation -> reservation.getPickUpBranchId().equals(branchId) &&
//                                       reservation.getPickUpDateTime().equals(currentDateTime)
//                )
//                .map(Reservation::getCar)
//                .collect(Collectors.toList());
//    }

    //    public List<Car> findCarsNeededForCurrentPickUpBranchAndDateTime(LocalDateTime pickUpDateTime) {
//    }
//
//
}
