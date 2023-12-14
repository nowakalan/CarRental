package pl.zdjavapol140.carrental.service.reservationManagement;

import org.springframework.stereotype.Component;
import pl.zdjavapol140.carrental.model.ReservationStatus;

@Component
public class StrategyEmptyTransfer implements ReservationManagerStrategy {

    @Override
    public boolean isAppropriate(ReservationWrapper reservationWrapper) {
        return reservationWrapper.previousReservation().isEmpty() &&
                reservationWrapper.nextReservation().isPresent() &&
                reservationWrapper.nextReservation().get().getStatus().equals(ReservationStatus.TRANSFER);
    }

    @Override
    public void manageReservations() {

    }
}
