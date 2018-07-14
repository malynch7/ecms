package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Dao.TravelDao;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.Event;
import com.beigeoranges.ecms.Model.TravelForm;
import com.beigeoranges.ecms.Model.UserForm;
import com.beigeoranges.ecms.Utils.ProfileValidator;
import com.beigeoranges.ecms.Utils.RegistrationValidator;
import com.beigeoranges.ecms.Utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class PlayerController {

    @Autowired
    private ProfileValidator profileValidator;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private TravelDao travelDao;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == UserForm.class) {
            dataBinder.setValidator(profileValidator);
        }
        // ...
    }




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
    public String viewEvent(@ModelAttribute Event event, Model model, Principal principal) {

        model.addAttribute("event", event);

        //check for player confirmation
        String userName = principal.getName();
        com.beigeoranges.ecms.Model.User player = (userDao.findUserAccount(userName));
        List<Event> confirmedEvents = eventDao.getConfirmedEvents(Math.toIntExact(player.getUserId()));
        int eventid = event.getEvent_id();
        boolean confirmed = false;
        for (Event confirmedEvent : confirmedEvents) {
            if(confirmedEvent.getEvent_id() == eventid){
                confirmed = true;
            }
        }
        model.addAttribute("confirmed",confirmed);

        return "player/viewEvent";
    }

    @RequestMapping(value = "/player/confirm", method = RequestMethod.POST)
    public String RSVP(@ModelAttribute Event event, Model model, Principal principal) {

        String userName = principal.getName();
        int userId = Math.toIntExact((userDao.findUserAccount(userName)).getUserId());
        eventDao.RSVP(event.getEvent_id(),userId);


        model.addAttribute("event",event);

        return "redirect:/player/dashboard";
    }

    @RequestMapping(value = "/player/viewTravelInfo", method = RequestMethod.POST)
    public String viewTravelInfo(@ModelAttribute Event event, Model model, Principal principal) {

        String userName = principal.getName();
        int userId = Math.toIntExact((userDao.findUserAccount(userName)).getUserId());

        TravelForm travelForm = travelDao.getTravelInfo(event.getEvent_id(), userId);
        model.addAttribute("travelForm", travelForm);
        model.addAttribute("event",event);

        return "player/viewTravelInfo";
    }

    @RequestMapping(value = "/player/editProfile", method = RequestMethod.GET)
    public String editProfile( Model model, Principal principal) {

        String userName = principal.getName();
        com.beigeoranges.ecms.Model.User user = userDao.findUserAccount(userName);
        UserForm userForm = new UserForm(user.getUserId(), user.getFirstName(), user.getFirstName(), true,
                user.getEmail(),"", "", "");
        model.addAttribute(userForm);

        return "player/editProfile";
    }

    @RequestMapping(value = "/player/editProfile", method = RequestMethod.POST)
    public String updateProfile(Model model, @ModelAttribute("userForm") @Validated UserForm userForm, BindingResult result,
                                Principal principal) {

        // Validate result
        if (result.hasErrors()) {
            return "player/editProfile";
        }
        try {
            String userName = principal.getName();
            int userId = Math.toIntExact((userDao.findUserAccount(userName)).getUserId());
            userDao.editProfile(userForm, userId);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "player/editProfile";
        }
        return "redirect:/player/dashboard";
    }



}
