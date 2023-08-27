package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime bookingDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startDateTime;

    private Long rentBranchId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime endDateTime;

    private Long returnBranchId;

    private BigDecimal totalPrice;

    private BigDecimal calculateTotalPrice() {
        return totalPrice;
    }

}
