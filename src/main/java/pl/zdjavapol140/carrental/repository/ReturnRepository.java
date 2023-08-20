package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.CarReturn;

public interface ReturnRepository extends JpaRepository<CarReturn, Long> {
}
