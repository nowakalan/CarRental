package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

public class ReservationGenerator {

    public static Reservation generateRandomReservation(Customer customer, Car car) {
        Random random = new Random();
        Reservation reservation = new Reservation();

        reservation.setStatus(ReservationStatus.values()[random.nextInt(ReservationStatus.values().length)]);
        reservation.setBookingDate(LocalDateTime.of(2023,8,30, 8, 0, 0).minusDays(random.nextInt(30))); // Rezerwacja w ciągu 30 dni przed 30.08.2023 godz. 8
        reservation.setPickUpDateTime(reservation.getBookingDate().plusDays(random.nextInt(10))); // Odbiór w ciągu następnych 10 dni
        reservation.setDropOffDateTime(reservation.getPickUpDateTime().plusDays(random.nextInt(14))); // Zwrot w ciągu następnych 14 dni
        reservation.setTotalPrice(BigDecimal.valueOf(random.nextDouble() * 1000)); // Cena losowa, do 1000
        reservation.setCustomer(customer);

        reservation.setCar(car);
        reservation.setPickUpBranchId(car.getRental().getBranches().get(random.nextInt(car.getRental().getBranches().size())).getId());
        reservation.setDropOffBranchId(car.getRental().getBranches().get(random.nextInt(car.getRental().getBranches().size())).getId());


        return reservation;
    }

}
