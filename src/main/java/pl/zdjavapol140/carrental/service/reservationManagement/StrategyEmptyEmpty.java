package pl.zdjavapol140.carrental.service.reservationManagement;

import org.springframework.stereotype.Component;

@Component
public class StrategyEmptyEmpty implements ReservationManagerStrategy {

    @Override
    public boolean isAppropriate(ReservationWrapper reservationWrapper) {
        return reservationWrapper.previousReservation().isEmpty() && reservationWrapper.nextReservation().isEmpty();
    }

    @Override
    public void manageReservations() {

    }
}
