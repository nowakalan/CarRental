package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ReservationGenerator {

    public static Reservation generateRandomReservation(Customer customer, Car car) {
        Random random = new Random();
        Reservation reservation = new Reservation();
        LocalDateTime currentDateTime = LocalDateTime.now();

        reservation.setStatus(ReservationStatus.values()[random.nextInt(ReservationStatus.values().length)]);
        reservation.setBookingDate(currentDateTime.minusDays(random.nextInt(3)));
        reservation.setPickUpDateTime(reservation.getBookingDate().plusDays(random.nextInt(10))); // Odbiór w ciągu następnych 10 dni

        // Ustawienie godziny odbioru na losową wartość od 0 do 23
        reservation.setPickUpDateTime(reservation.getPickUpDateTime().plusHours(random.nextInt(24)));
        reservation.setDropOffDateTime(reservation.getPickUpDateTime().plusDays(random.nextInt(14))); // Zwrot w ciągu następnych 14 dni
        if (Duration.between(reservation.getPickUpDateTime(), reservation.getDropOffDateTime()).toHours() < 1) {
            reservation.setDropOffDateTime(reservation.getPickUpDateTime().plusHours(1)); // Minimum godzina wynajmu
        }

        reservation.setCustomer(customer);

        reservation.setCar(car);
        List<Long> branchIds = car.getRental().getBranches().stream().map(Branch::getId).toList();
        reservation.setPickUpBranchId(branchIds.get(random.nextInt(branchIds.size())));
        reservation.setDropOffBranchId(branchIds.get(random.nextInt(branchIds.size())));

        Duration duration = Duration.between(reservation.getPickUpDateTime(), reservation.getDropOffDateTime());
        long days = duration.toDays();
        reservation.setTotalPrice(car.getSize().getPrice().multiply(BigDecimal.valueOf(days)));

        return reservation;
    }

}
