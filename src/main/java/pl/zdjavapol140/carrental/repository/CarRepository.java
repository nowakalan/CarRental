package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.CarSize;
import pl.zdjavapol140.carrental.model.CarTransmissionType;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Override
    List<Car> findAll();

    List<Car> findCarsByColor(String color);

    List<Car> findCarsByBrand(String brand);

    List<Car> findCarsByModel(String model);

    List<Car> findCarsBySize(CarSize size);
    List<Car> findCarsBySizeIn(List<CarSize> sizes);

    List<Car> findCarsByPriceIsLessThanEqual(BigDecimal maxPrice);

    List<Car> findCarsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Car> findCarsByPriceIsGreaterThanEqual(BigDecimal minPrice);

    List<Car> findCarsByIdIsNotIn(List<Long> carIds);

    List<Car> findCarsByRentalId(Long rentalId);

    List<Car> findCarsByTransmissionType(CarTransmissionType type);
    List<Car> findCarsByProductionYear(Integer productionYear);
    List<Car> findCarsByMileageLessThan(Double mileage);

    List<Car> findCarsByMileageGreaterThan(Double mileage);


}
