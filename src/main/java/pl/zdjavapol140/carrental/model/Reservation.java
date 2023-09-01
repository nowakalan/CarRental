package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH-mm-SS")
    private LocalDateTime bookingDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH-mm-SS")
    private LocalDateTime pickUpDateTime;

    private Long pickUpBranchId;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH-mm-SS")
    private LocalDateTime dropOffDateTime;

    private Long dropOffBranchId;

    private BigDecimal totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

//    private BigDecimal calculateTotalPrice() {
//        return totalPrice;
//    }

}
