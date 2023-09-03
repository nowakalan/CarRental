package pl.zdjavapol140.carrental.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.service.BranchService;
import pl.zdjavapol140.carrental.service.CarService;

import java.util.List;

@Controller
public class WebController {

    private final BranchService branchService;
    private final CarService carService;

    public WebController(BranchService branchService, CarService carservice) {
        this.branchService = branchService;
        this.carService = carservice;
    }


    @GetMapping("/")
    public String index(Model model) {

        List<Branch> allBranches = this.branchService.getAllBranches();
        model.addAttribute("allBranches", allBranches);

        return "index";
    }

    @GetMapping("/search")
        public String search(@RequestParam String pickUpLocation, @RequestParam String dropOffLocation,
                @RequestParam String pickUpDateTime, @RequestParam String dropOffDateTime, Model model)
        {
            List<Car> carList = carService.searchCars(pickUpLocation, dropOffLocation, pickUpDateTime, dropOffDateTime);
            model.addAttribute("carList", carList);

            return "searchResults";
        }
    }
