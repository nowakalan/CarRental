package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate rentDate;

    @OneToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    private String note;

    public CarRent(Reservation reservation, LocalDate rentDate, Employee employee) {
        this.reservation = reservation;
        this.rentDate = rentDate;
        this.employee = employee;
    }

    public CarRent(Reservation reservation, LocalDate rentDate, Employee employee, String note) {
        this.reservation = reservation;
        this.rentDate = rentDate;
        this.employee = employee;
        this.note = note;
    }
}
