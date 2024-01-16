package pl.zdjavapol140.carrental.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cars")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;

    @Enumerated(value = EnumType.STRING)
    private CarSize size;

    @Enumerated(value = EnumType.STRING)
    private CarTransmissionType transmissionType;

    private String registrationNumber;
    private Integer productionYear;
    private String color;
    private Integer mileage;

    private BigDecimal price;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();

    public Car(String brand, String model, CarSize size, Integer productionYear, String color) {
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.productionYear = productionYear;
        this.color = color;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", size=" + size +
                ", transmissionType=" + transmissionType +
                ", productionYear=" + productionYear +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", price=" + price +
                ", rental=" + rental +
                '}';
    }
}
