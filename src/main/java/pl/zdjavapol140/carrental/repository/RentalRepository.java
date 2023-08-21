package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
