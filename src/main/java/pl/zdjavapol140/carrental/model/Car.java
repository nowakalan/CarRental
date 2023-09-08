package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;

    public Car(String brand, String model, CarBodyType bodyType, Integer productionYear, String color) {
        this.brand = brand;
        this.model = model;
        this.bodyType = bodyType;
        this.productionYear = productionYear;
        this.color = color;
    }

    @Enumerated(value = EnumType.STRING)
    private CarBodyType bodyType;

    @Enumerated(value = EnumType.STRING)
    private CarTransmissionType transmissionType;

    private Integer productionYear;
    private String color;
    private Double mileage;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();


}
