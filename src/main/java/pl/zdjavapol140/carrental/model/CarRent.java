package pl.zdjavapol140.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

//    @JoinColumn(name = "reservation_id")
//    private Long reservationId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime rentDateTime;

    @Column(name = "employee")
    private Long employeeId;

    private String note;

    public CarRent(Reservation reservation, LocalDateTime rentDateTime, Long employeeId) {
        this.reservation = reservation;
        this.rentDateTime = rentDateTime;
        this.employeeId = employeeId;
    }

    public CarRent(Reservation reservation, LocalDateTime rentDateTime, Long employeeId, String note) {
        this.reservation = reservation;
        this.rentDateTime = rentDateTime;
        this.employeeId = employeeId;
        this.note = note;
    }
}
