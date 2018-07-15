package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.TravelDao;
import com.beigeoranges.ecms.Model.TravelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class TravelController {

    @Autowired
    private TravelDao travelDao;

    @RequestMapping(value = "/admin/createTravel", method = RequestMethod.GET)
    public String viewCreateTravelPage(Model model) {
        TravelForm travel = new TravelForm();

        model.addAttribute("travel", travel);

        return "admin/createTravel";
    }

    @RequestMapping(value = "/admin/createTravel", method = RequestMethod.POST)
    public String saveTravel(Model model, Principal principal, @ModelAttribute(value="travel") TravelForm travel) {


            travelDao.createFlight(travel);


        return "redirect:/admin/dashboard";
    }
}
