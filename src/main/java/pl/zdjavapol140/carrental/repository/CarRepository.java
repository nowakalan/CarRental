package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findCarsByColor(String color);

    List<Car> findCarsByBrand(String brand);

    List<Car> findCarsByModel(String model);

    List<Car> findCarsByBodyType(String bodyType);

    List<Car> findCarsByPriceIsLessThanEqual(BigDecimal price);
    List<Car> findCarsByBranchId(Long branchId);
}
