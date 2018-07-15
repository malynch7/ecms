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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.security.Signature;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "/admin/createEvent", method = RequestMethod.GET)
    public String viewCreateEventPage(Model model) {
        Event event = new Event();

        model.addAttribute("event", event);

        return "admin/createEvent";
    }

    @RequestMapping(value = "/admin/createEvent", method = RequestMethod.POST)
    public String saveEvent(Model model, Principal principal, @ModelAttribute(value = "event") Event event, BindingResult result) {

        // Validate result
        if (result.hasErrors()) {
            return "admin/createEvent";
        }
        try {
            String username = principal.getName();
            event.setAdmin_id(userDao.getUserIdByEmail(username));
            eventDao.createEvent(event);

        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());

            return "admin/createEvent";
        }
        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/admin/EventSelected", method = RequestMethod.GET)
    public String viewEventSelected(@RequestParam("id") String eventid, Model model) {

        List<User> UninvitedList = userDao.getUninvitedUsers(Integer.parseInt(eventid));
        model.addAttribute("Uninvited", UninvitedList);

        List<User> InvitedList = userDao.getInvitedUsers(Integer.parseInt(eventid));
        model.addAttribute("Invited", InvitedList);

        List<User> RSVPList = userDao.getRSVPUsers(Integer.parseInt(eventid));
        model.addAttribute("RSVPd", RSVPList);

        return "admin/EventSelected";
    }

    @RequestMapping(value = "/admin/EventSelected", method = RequestMethod.POST)
    public String InvitePlayer(Model model, @ModelAttribute(value = "invite") String email, BindingResult result) {

        // Validate result
        if (result.hasErrors()) {
            return "admin/EventSelected";
        }
        try {
            eventDao.Invite(1,userDao.getUserIdByEmail(email));

        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());

            return "admin/EventSelected";
        }
        return "redirect:/admin/EventSelected";
    }
}
