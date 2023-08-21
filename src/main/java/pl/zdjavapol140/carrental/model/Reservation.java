package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReservationStatus status;

    private LocalDateTime bookingDateTime;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "car_id")
    private Long carId;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Column(name = "rent_division_id")
    private Long rentDivisionId;

    @Column(name = "return_division_id")
    private Long returnDivisionId;

    private BigDecimal totalPrice;

}
