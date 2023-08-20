package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Booking;
import pl.zdjavapol140.carrental.model.Rent;

public interface RentRepository extends JpaRepository<Rent, Long> {
}
