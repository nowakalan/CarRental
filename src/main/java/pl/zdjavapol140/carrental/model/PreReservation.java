package pl.zdjavapol140.carrental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreReservation {

    private Car car;
    private Customer customer;
    private LocalDateTime pickUpDateTime;
    private LocalDateTime dropOffDateTime;
    private Branch pickUpBranch;
    private Branch dropOffBranch;
    private BigDecimal totalPrice;

    @Override
    public String toString() {
        return "PreReservation{" +
                "car=" + car +
                ", customer=" + customer +
                ", pickUpDateTime=" + pickUpDateTime +
                ", dropOffDateTime=" + dropOffDateTime +
                ", pickUpBranch=" + pickUpBranch +
                ", dropOffBranch=" + dropOffBranch +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
