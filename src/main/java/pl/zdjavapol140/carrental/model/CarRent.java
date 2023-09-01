package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
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


}
