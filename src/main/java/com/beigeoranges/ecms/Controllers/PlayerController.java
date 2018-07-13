package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.Event;
import com.beigeoranges.ecms.Utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class PlayerController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;



    @RequestMapping(value = {"/player", "player/dashboard"}, method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // Authentication after user login successfully.
        String userName = principal.getName();
        System.out.println("User Name: " + userName);
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        com.beigeoranges.ecms.Model.User player = (userDao.findUserAccount(userName));

        //retrieve player's invited events

        List<Event> invitedEvents = eventDao.getInvitedEvents(Math.toIntExact(player.getUserId()));
        model.addAttribute("isInvitedEmpty", invitedEvents.isEmpty());
        model.addAttribute("invitedEvents", invitedEvents);

        //retrieve player's confirmed events
        List<Event> confirmedEvents = eventDao.getConfirmedEvents(Math.toIntExact(player.getUserId()));
        model.addAttribute("isConfirmedEmpty", confirmedEvents.isEmpty());
        model.addAttribute("confirmedEvents", confirmedEvents);


        return "player/dashboard";
    }

    @RequestMapping(value = "/player/viewEvent", method = RequestMethod.POST)
    public String viewEvent(@RequestParam("invitedEvent") Event event, Model model) {

        System.out.println(event.getEvent_name());

        model.addAttribute("event", event);

        return "player/viewEvent";
    }
}
