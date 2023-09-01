package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByCarId(Long carId);

    List<Reservation> findReservationsByStatus(ReservationStatus status);

    List<Reservation> findReservationsByCustomerId(Long customerId);

    List<Reservation> findReservationsByPickUpBranchId(Long branchId);

    List<Reservation> findReservationsByDropOffBranchId(Long branchId);

    List<Reservation> findReservationsByPickUpDateTimeBetween(LocalDateTime currentPickUpDateTime, LocalDateTime currentDropOffDateTime);

    List<Reservation> findReservationsByDropOffDateTimeBetween(LocalDateTime currentPickUpDateTime, LocalDateTime currentDropOffDateTime);

    List<Reservation> findReservationsByBookingDate(LocalDateTime bookingDate);


    @Query(value = """
            SELECT DISTINCT r.car FROM Reservation r
            WHERE (r.status = 'SET' OR r.status = 'IN_PROGRESS')
            AND ((:currentPickUpDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
                OR (:currentDropOffDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
                OR (r.pickUpDateTime >= :currentPickUpDateTime AND r.dropOffDateTime <= :currentDropOffDateTime))
                    """)
    List<Car> findUnavailableCars(@Param("currentPickUpDateTime") LocalDateTime currentPickUpDateTime, @Param("currentDropOffDateTime") LocalDateTime currentDropOffDateTime);


}
