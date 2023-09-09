package pl.zdjavapol140.carrental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CarSearch {
    private LocalDateTime currentPickUpDateTime;
    private LocalDateTime currentDropOffDateTime;
    private Long currentPickUpBranchId;
    private Long currentDropOffBranchId;
}
