package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.model.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    Rental findRentalById(Long id);

}
