package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Booking;
import pl.zdjavapol140.carrental.model.Car;

public interface CarRepository extends JpaRepository <Car, Long> {
}
