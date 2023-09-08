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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "car_returns")
public class CarReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH-mm")
    private LocalDateTime dropOffDate;

    private BigDecimal surcharge;

    private String notes;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarReturn carReturn = (CarReturn) o;
        return Objects.equals(getId(), carReturn.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}


