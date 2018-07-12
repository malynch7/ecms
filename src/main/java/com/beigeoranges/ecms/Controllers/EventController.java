package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.ArchivedEventDao;
import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.ArchivedEvent;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class EventController {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "/admin/createEvent", method = RequestMethod.GET)
    public String viewCreateEventPage(Model model, Principal principal) {
        Event event = new Event();

        model.addAttribute("event", event);

        return "admin/createEvent";
    }

    @RequestMapping(value = "/admin/createEvent", method = RequestMethod.POST)
    public String saveEvent(Model model, Principal principal, @ModelAttribute(value="event") Event event, BindingResult result, final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
       //     System.out.println("Error in if");
            return "admin/createEvent";
        }
        try {
            String username = principal.getName();
            event.setAdmin_id(userDao.getUserIdByEmail(username));
            eventDao.createEvent(event);
         //   System.out.println("try");

        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
        //    System.out.println("Error in catch");

            return "admin/createEvent";
        }
        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/admin/EventSelected", method = RequestMethod.GET)
    public String viewEventSelected(Model model) {


        return "admin/EventSelected";
    }

}
