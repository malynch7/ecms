package com.beigeoranges.ecms.Controllers;

import com.beigeoranges.ecms.Dao.EventDao;
import com.beigeoranges.ecms.Model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventController {

    @Autowired
    private EventDao eventDao;

    @RequestMapping(value = "/createEvent", method = RequestMethod.GET)
    public String viewCreateEventPage(Model model) {
        Event event = new Event();

        model.addAttribute("event", event);

        return "createEvent";
    }

    @RequestMapping(value = "/createEvent", method = RequestMethod.POST)
    public String saveEvent(Model model, @ModelAttribute(value="event") Event event, BindingResult result, final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
            return "createEvent";
        }
        try {
            eventDao.createEvent(event);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "createEvent";
        }
        return "redirect:/createEvent";
    }
}
