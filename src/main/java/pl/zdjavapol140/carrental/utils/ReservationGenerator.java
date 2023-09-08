package pl.zdjavapol140.carrental.utils;

import pl.zdjavapol140.carrental.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ReservationGenerator {

    public static Reservation generateRandomReservation(Customer customer, Car car) {
        Random random = new Random();
        Reservation reservation = new Reservation();

        reservation.setStatus(ReservationStatus.values()[random.nextInt(ReservationStatus.values().length)]);
        reservation.setBookingDate(LocalDateTime.of(2023,7,30, 8, 0, 0).minusDays(random.nextInt(3))); // Rezerwacja w ciągu 30 dni przed 30.08.2023 godz. 8
        reservation.setPickUpDateTime(reservation.getBookingDate().plusDays(random.nextInt(10))); // Odbiór w ciągu następnych 10 dni
        reservation.setDropOffDateTime(reservation.getPickUpDateTime().plusDays(random.nextInt(14))); // Zwrot w ciągu następnych 14 dni
        reservation.setTotalPrice(BigDecimal.valueOf(random.nextDouble() * 1000)); // Cena losowa, do 1000
        reservation.setCustomer(customer);

        reservation.setCar(car);
        List<Long> branchIds = car.getRental().getBranches().stream().map(Branch::getId).toList();
        reservation.setPickUpBranchId(branchIds.get(random.nextInt(branchIds.size())));
        reservation.setDropOffBranchId(branchIds.get(random.nextInt(branchIds.size())));


        return reservation;
    }

}
