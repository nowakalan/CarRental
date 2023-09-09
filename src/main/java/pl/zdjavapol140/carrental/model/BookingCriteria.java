package pl.zdjavapol140.carrental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingCriteria {

    private LocalDateTime currentPickUpDateTime;
    private LocalDateTime currentDropOffDateTime;
    private Long currentPickUpBranchId;
    private Long currentDropOffBranchId;

}
