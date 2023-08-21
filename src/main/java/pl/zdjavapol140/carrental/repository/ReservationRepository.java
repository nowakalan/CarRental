package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
