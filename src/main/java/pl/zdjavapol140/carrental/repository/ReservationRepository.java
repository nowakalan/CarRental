package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByCarId(Long carId);

    List<Reservation> findReservationsByStatusIs(ReservationStatus status);

    List<Reservation> findReservationsByCustomerId(Long customerId);

    List<Reservation> findReservationsByEndDateTimeIsBefore(LocalDateTime searchedStartDateTime);

    List<Reservation> findReservationsByStartDateTimeIsAfter(LocalDateTime searchedEndDateTime);

    List<Reservation> findReservationsByStartDateTimeBetween(LocalDateTime currentStartDateTime, LocalDateTime currentEndDateTime);
    List<Reservation> findReservationsByEndDateTimeBetween(LocalDateTime currentStartDateTime, LocalDateTime currentEndDateTime);
    List<Reservation> findReservationsByRentBranchId(Long branchId);
    List<Reservation> findReservationsByReturnBranchId(Long branchId);

}
