package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zdjavapol140.carrental.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
