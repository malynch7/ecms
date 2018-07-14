package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.ArchivedEventDao;
import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.ArchivedEvent;
import com.beigeoranges.ecms.Model.Event;
import com.beigeoranges.ecms.Model.User;
import com.beigeoranges.ecms.Utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

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

    @RequestMapping(value = "/admin/EventSelected?id={eventid}", method = RequestMethod.GET)
    public @ResponseBody String viewEventSelected(@PathVariable(value="eventid") String eventid, Model model) {


        List<User> userList = userDao.getAllUsers();

        model.addAttribute("player", userList);


        return "admin/EventSelected";
    }

}
