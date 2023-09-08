package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.Reservation;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Override
    List<Car> findAll();

    List<Car> findCarsByColor(String color);

    List<Car> findCarsByBrand(String brand);

    List<Car> findCarsByModel(String model);

    List<Car> findCarsByBodyType(String bodyType);

    List<Car> findCarsByPriceIsLessThanEqual(BigDecimal price);

    List<Car> findCarsByIdIsNotIn(List<Long> carIds);

    List<Car> findCarsByRentalId(Long rentalId);

    @Query(value = "SELECT c FROM Car c WHERE (c not in :unavailableCars)")
    List<Car> findAvailableCars(@Param("unavailableCars") List<Car> unavailableCars);

    Car findCarById(Long carId);
}
