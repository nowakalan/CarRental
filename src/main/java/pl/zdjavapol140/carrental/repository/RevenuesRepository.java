package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Booking;
import pl.zdjavapol140.carrental.model.Revenues;

public interface RevenuesRepository extends JpaRepository<Revenues, Long> {
}
