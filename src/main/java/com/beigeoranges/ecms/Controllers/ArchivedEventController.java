package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.ArchivedEventDao;
import com.beigeoranges.ecms.Model.ArchivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ArchivedEventController {

    @Autowired
    private ArchivedEventDao archEventDao;

    @RequestMapping(value = "admin/archiveEvent", method = RequestMethod.GET)
    public String viewArchiveEventPage(Model model, Principal principal) {
        ArchivedEvent aEvent = new ArchivedEvent();
        return "admin/archiveEvent";
    }
}
