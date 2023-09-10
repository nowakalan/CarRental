package pl.zdjavapol140.carrental.service.reservationManagement;

import org.springframework.stereotype.Component;
import pl.zdjavapol140.carrental.model.ReservationStatus;

@Component
public class StrategyEmptyNontransfer implements ReservationManagerStrategy {

    @Override
    public boolean isAppropriate(ReservationWrapper reservationWrapper) {

        return reservationWrapper.previousReservation().isEmpty() &&
                reservationWrapper.nextReservation().isPresent() &&
                !reservationWrapper.nextReservation().get().getStatus().equals(ReservationStatus.TRANSFER) &&
                !reservationWrapper.nextReservation().get().getStatus().equals(ReservationStatus.CANCELED);
    }

    @Override
    public void manageReservations() {

    }
}
