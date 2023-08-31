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

    List<Reservation> findReservationsByStatusIs(ReservationStatus status);

    List<Reservation> findReservationsByCustomerId(Long customerId);



//    List<Reservation> findReservationsByDropOffDateTimeAfter(LocalDateTime currentDropOffDateTime);
//
//    List<Reservation> findReservationsByPickUpDateTimeBefore(LocalDateTime currentPickUpDateTime);
//
//    List<Reservation> findReservationsByPickUpDateTimeBetween(LocalDateTime currentPickUpDateTime, LocalDateTime currentDropOffDateTime);
//    List<Reservation> findReservationsByDropOffDateTimeBetween(LocalDateTime currentPickUpDateTime, LocalDateTime currentDropOffDateTime);

    //TODO kiedyś: Optymalizacja wykorzystania floty: samochód jak najkrócej stoi bezczynnie; ograniczony czasowo status samochodu UNAVAILABLE w związku z przeglądem/naprawą

    @Query(value = """
                SELECT DISTINCT r.car FROM Reservation r
                WHERE (r.status = 'SET' OR r.status = 'IN_PROGRESS')
                AND ((:currentPickUpDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
                    OR (:currentDropOffDateTime BETWEEN r.pickUpDateTime AND r.dropOffDateTime)
                    OR ((r.pickUpDateTime BETWEEN :currentPickUpDateTime AND :currentDropOffDateTime)
                        AND (r.dropOffDateTime BETWEEN :currentPickUpDateTime AND :currentDropOffDateTime)))""")
    List<Car> findUnavailableCars(@Param("currentPickUpDateTime") LocalDateTime currentPickUpDateTime, @Param("currentDropOffDateTime") LocalDateTime currentDropOffDateTime);
//    @Query(value = """
//SELECT MAX dropOffDateTime FROM Reservation r
//WHERE
//""")
    List<Reservation> findActiveReservationsByCarIdAndDropOffDateTimeBefore(@Param("carId") Long carId, @Param("currentDateTime") LocalDateTime currentDateTime);

    List<Reservation> findReservationsByPickUpBranchId(Long branchId);

    List<Reservation> findReservationsByDropOffBranchId(Long branchId);

}
