package com.beigeoranges.ecms.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/css/login.css", method = RequestMethod.GET)
public class CSSController {
    public String getCSS(Model model)
    {
        return "../static/css/login.css";
    }
}
