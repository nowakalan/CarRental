package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bookingDate;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "car_id")
    private Long carId;


    private LocalDate rentStartDate;
    private LocalDate rentEndDate;

//    @Column(name = "division_id")
    private Long rentDivisionId;

//    @Column(name = "division_id")
//    private Long returnDivisionId;

    private BigDecimal totalPrice;
}
