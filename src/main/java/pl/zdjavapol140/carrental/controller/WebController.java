package pl.zdjavapol140.carrental.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.service.BranchService;
import pl.zdjavapol140.carrental.service.CarService;
import pl.zdjavapol140.carrental.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WebController {

    private final BranchService branchService;
    private final ReservationService reservationService;
    private final CarService carService;

    public WebController(BranchService branchService, ReservationService reservationService, CarService carService) {
        this.branchService = branchService;
        this.reservationService = reservationService;
        this.carService = carService;
    }


    @GetMapping("/")
    public String index(Model model) {

        List<Branch> allBranches = this.branchService.getAllBranches();
        model.addAttribute("allBranches", allBranches);

        return "index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }


//    @GetMapping("/search")
//    public String search(@RequestParam LocalDateTime currentPickUpDateTime, @RequestParam LocalDateTime currentDropOffDateTime,
//                         @RequestParam Long currentPickUpBranchId, @RequestParam Long currentDropOffBranchId, Model model) {
//        List<Car> carList = reservationService.findAvailableCars(currentPickUpDateTime, currentDropOffDateTime, currentPickUpBranchId, currentDropOffBranchId);
//        model.addAttribute("carList", carList);
//
//        return "searchResults";
//    }


//    @GetMapping("/cars")
//    public String getCars(Model model){
//        model.addAttribute("cars", carService.getAll());
//        return "cars-list";
//    }

}
