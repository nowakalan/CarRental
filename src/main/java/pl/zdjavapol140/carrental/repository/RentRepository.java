package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.CarRent;

public interface RentRepository extends JpaRepository<CarRent, Long> {
}
