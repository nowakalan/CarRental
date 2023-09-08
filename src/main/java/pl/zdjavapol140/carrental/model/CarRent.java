package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "car_rents")
public class CarRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH-mm")
    private LocalDateTime pickUpDate;

    @OneToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    private String note;

    public CarRent(Reservation reservation, LocalDateTime pickUpDate, Employee employee, String note) {
        reservation.setStatus(ReservationStatus.IN_PROGRESS);
        this.reservation = reservation;
        this.pickUpDate = pickUpDate;
        this.employee = employee;
        this.note = note;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRent carRent = (CarRent) o;
        return Objects.equals(getId(), carRent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
