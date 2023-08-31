package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate bookingDate;

    //    private Long customerId;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

//    private Long carId;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startDate;

    private Long rentBranchId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime endDate;

    private Long returnBranchId;

    private BigDecimal totalPrice;

    private BigDecimal calculateTotalPrice() {
        return totalPrice;
    }

}
