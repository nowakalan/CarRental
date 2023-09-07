package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.zdjavapol140.carrental.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByCarId(Long carId);

    List<Reservation> findReservationsByStatusEquals(ReservationStatus status);

    List<Reservation> findReservationsByStatusNot(ReservationStatus status);

    List<Reservation> findReservationsByCustomerId(Long customerId);

    List<Reservation> findReservationsByPickUpBranchId(Long branchId);

    List<Reservation> findReservationsByDropOffBranchId(Long branchId);

    List<Reservation> findReservationsByPickUpDateTimeBetween(LocalDateTime minDateTime, LocalDateTime maxDateTime);

    List<Reservation> findReservationsByDropOffDateTimeBetween(LocalDateTime minDateTime, LocalDateTime maxDateTime);

    List<Reservation> findReservationsByBookingDateBetween(LocalDateTime minDate, LocalDateTime maxDate);

    List<Reservation> findReservationsByCarSize(CarSize size);

    List<Reservation> findReservationsByCar_TransmissionType(CarTransmissionType transmissionType);

    @Query(value = """
            SELECT DISTINCT r.car FROM Reservation r
            WHERE (r.status = 'SET' OR r.status = 'IN_PROGRESS')
            AND ((:currentPickUpDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
            OR (:currentDropOffDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
                OR (r.pickUpDateTime >= :currentPickUpDateTime AND r.dropOffDateTime <= :currentDropOffDateTime))
                    """)
    List<Car> findUnavailableCars(@Param("currentPickUpDateTime") LocalDateTime currentPickUpDateTime, @Param("currentDropOffDateTime") LocalDateTime currentDropOffDateTime);

    @Query(value = """
            SELECT DISTINCT r FROM Reservation r
            WHERE (r.status = 'CANCELLED')
            OR ((:currentPickUpDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
                OR (:currentDropOffDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
                OR (r.pickUpDateTime >= :currentPickUpDateTime AND r.dropOffDateTime <= :currentDropOffDateTime))
                    """)
    List<Reservation> findNotMatchingReservations(@Param("currentPickUpDateTime") LocalDateTime currentPickUpDateTime, @Param("currentDropOffDateTime") LocalDateTime currentDropOffDateTime);

}
