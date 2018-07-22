package com.beigeoranges.ecms.Controllers;


import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.Invitation;
import com.beigeoranges.ecms.Model.TravelForm;
import com.beigeoranges.ecms.Model.User;
import com.beigeoranges.ecms.Model.UserForm;
import com.beigeoranges.ecms.Utils.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class InvitationController {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "/admin/invite", method = RequestMethod.POST)
    public String InvitePlayer(Model model, @ModelAttribute("invitation") Invitation invitation) {


        User user = userDao.findUserAccount(invitation.getEmail());
        if(user != null){
            eventDao.Invite(invitation.getEventId(),Math.toIntExact(user.getUserId()));
        }
        model.addAttribute("event", eventDao.getEventById(invitation.getEventId()));

        List<User> invitedPlayers = userDao.getInvitedPlayers(invitation.getEventId());
        model.addAttribute("invitedPlayers", invitedPlayers);

        List<User> confirmedPlayers = userDao.getConfirmedPlayers(invitation.getEventId());
        model.addAttribute("confirmedPlayers", confirmedPlayers);

        model.addAttribute("travelForm", new TravelForm());
        model.addAttribute("invitation", new Invitation());

        return "/admin/viewEvent";
    }
}
