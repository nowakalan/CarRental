package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Long> getCarIdsByStartDateTimeBefore(LocalDateTime changeStatusDateTime);
    public List<Long> findCarIdByStatus(ReservationStatus status);

}
