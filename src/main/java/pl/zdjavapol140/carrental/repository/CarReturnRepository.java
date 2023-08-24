package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.CarReturn;

public interface CarReturnRepository extends JpaRepository<CarReturn, Long> {
}
