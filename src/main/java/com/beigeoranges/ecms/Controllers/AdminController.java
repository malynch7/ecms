package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.ArchivedEventDao;
import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Dao.TravelDao;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.*;
import com.beigeoranges.ecms.Utils.ProfileValidator;
import com.beigeoranges.ecms.Utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private TravelDao travelDao;

    @Autowired
    private ArchivedEventDao archivedEventDao;

    @Autowired
    private ProfileValidator profileValidator;

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

    @RequestMapping(value = {"/admin", "/admin/dashboard"}, method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        org.springframework.security.core.userdetails.User loginedUser = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        List<Event> eventList = eventDao.getAllEvents();
        model.addAttribute("events", eventList);

        return "admin/dashboard";
    }

    @RequestMapping(value = "/admin/editProfile", method = RequestMethod.GET)
    public String editProfile(Model model, Principal principal) {

        String userName = principal.getName();
        com.beigeoranges.ecms.Model.User user = userDao.findUserAccount(userName);
        UserForm userForm = new UserForm(user.getUserId(), user.getFirstName(), user.getFirstName(), true,
                user.getEmail(),"", "", "");
        model.addAttribute(userForm);

        return "admin/editProfile";
    }

    @RequestMapping(value = "/admin/editProfile", method = RequestMethod.POST)
    public String updateProfile(Model model, @ModelAttribute("userForm") @Validated UserForm userForm, BindingResult result,
                                Principal principal) {

        // Validate result
        if (result.hasErrors()) {
            return "admin/editProfile";
        }
        try {
            String userName = principal.getName();
            int userId = Math.toIntExact((userDao.findUserAccount(userName)).getUserId());
            userDao.editProfile(userForm, userId);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "admin/editProfile";
        }
        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/admin/archiveEvent", method = RequestMethod.POST)
    public String archiveEvent(@ModelAttribute("event") Event event, Model model) {

        archivedEventDao.archiveEvent(event);

        return "redirect:/admin/dashboard";
    }


    @RequestMapping(value = "/admin/createEvent", method = RequestMethod.GET)
    public String viewCreateEventPage(Model model) {
        Event event = new Event();

        model.addAttribute("event", event);

        return "admin/createEvent";
    }

    @RequestMapping(value = "/admin/createEvent", method = RequestMethod.POST)
    public String saveEvent(Model model, Principal principal, @ModelAttribute(value = "event") Event event, BindingResult result) {

        String username = principal.getName();
        event.setAdmin_id(userDao.getUserIdByEmail(username));
        eventDao.createEvent(event);

        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/admin/viewEvent", method = RequestMethod.POST)
    public String viewEventSelected(@ModelAttribute("event") Event event, Model model) {

        List<User> invitedPlayers = userDao.getInvitedPlayers(event.getEvent_id());
        model.addAttribute("invitedPlayers", invitedPlayers);

        List<User> confirmedPlayers = userDao.getConfirmedPlayers(event.getEvent_id());
        model.addAttribute("confirmedPlayers", confirmedPlayers);

        model.addAttribute("travelForm", new TravelForm());
        model.addAttribute("invitation", new Invitation());

        return "admin/viewEvent";
    }

    @RequestMapping(value = "/admin/invite", method = RequestMethod.POST)
    public String InvitePlayer(Model model, @ModelAttribute("invitation") Invitation invitation) {

            eventDao.Invite(invitation.getEvent().getEvent_id(),userDao.getUserIdByEmail(invitation.getEmail()));
            model.addAttribute("event", invitation.getEvent());

        return "redirect:/admin/viewEvent";
    }

    @RequestMapping(value = "/admin/createTravel", method = RequestMethod.POST)
    public String createTravel(Model model, @ModelAttribute("travelForm") TravelForm travelForm) {

        model.addAttribute("travelForm", travelForm);

        return "/admin/createTravel";
    }

    @RequestMapping(value = "/admin/uploadTravel", method = RequestMethod.POST)
    public String uploadTravel(Model model, @ModelAttribute("travelForm") TravelForm travelForm) {

         travelDao.createFlight(travelForm);

         eventDao.getEventById(travelForm.getEventId());
        model.addAttribute("event", eventDao.getEventById(travelForm.getEventId()));

        List<User> invitedPlayers = userDao.getInvitedPlayers(travelForm.getEventId());
        model.addAttribute("invitedPlayers", invitedPlayers);

        List<User> confirmedPlayers = userDao.getConfirmedPlayers(travelForm.getEventId());
        model.addAttribute("confirmedPlayers", confirmedPlayers);

        model.addAttribute("travelForm", new TravelForm());
        model.addAttribute("invitation", new Invitation());

        return "/admin/viewEvent";
    }
}
