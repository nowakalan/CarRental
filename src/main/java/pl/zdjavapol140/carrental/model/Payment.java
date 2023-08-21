package pl.zdjavapol140.carrental.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {


    private Long id;

    private BigDecimal paymentAmount;

    private LocalDateTime paymentDateTime;
}
