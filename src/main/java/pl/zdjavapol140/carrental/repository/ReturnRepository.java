package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Booking;
import pl.zdjavapol140.carrental.model.Return;

public interface ReturnRepository extends JpaRepository<Return, Long> {
}
