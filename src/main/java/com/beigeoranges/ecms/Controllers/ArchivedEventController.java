package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Model.ArchivedEvent;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class ArchivedEventController {

    @Autowired
    private EventDao eventDao;

    @RequestMapping(value = "admin/archiveEvent", method = RequestMethod.GET)
    public String viewArchiveEventPage(Model model) {
        ArchivedEvent aEvent = new ArchivedEvent();
        List<String> curEvents = eventDao.curEvents();
        model.addAttribute("Events", curEvents);
        return "admin/archiveEvent";
    }
}
