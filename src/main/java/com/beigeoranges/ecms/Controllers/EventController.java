package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.Event;
import com.beigeoranges.ecms.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

        List<User> InvitedList = userDao.getInvitedPlayers(Integer.parseInt(eventid));
        model.addAttribute("Invited", InvitedList);

        List<User> RSVPList = userDao.getConfirmedPlayers(Integer.parseInt(eventid));
        model.addAttribute("RSVPd", RSVPList);

        return "admin/viewEvent";
    }

    @RequestMapping(value = "/admin/invitePlayers", method = RequestMethod.POST)
    public String InvitePlayer(Model model, @ModelAttribute(value = "email") String email, @ModelAttribute("event") Event event) {

            System.out.println(userDao.getUserIdByEmail(email));
            eventDao.Invite(event.getEvent_id(),userDao.getUserIdByEmail(email));

        return "redirect:/admin/viewEvent";
    }
}
