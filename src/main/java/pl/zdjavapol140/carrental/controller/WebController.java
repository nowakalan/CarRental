package pl.zdjavapol140.carrental.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.service.BranchService;

import java.util.List;

@Controller
public class WebController {

    private final BranchService branchService;

    public WebController(BranchService branchService) {
        this.branchService = branchService;
    }


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/search")
    public String showSearchPage(Model model) {
        List<Branch> pickupBranches = branchService.getAllBranches();
        List<Branch> returnBranches = branchService.getAllBranches();

        model.addAttribute("pickupBranches", pickupBranches);
        model.addAttribute("returnBranches", returnBranches);

        return "search";
    }
}
