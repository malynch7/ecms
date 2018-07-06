package com.beigeoranges.ecms.Controllers;


import java.security.Principal;

import com.beigeoranges.ecms.Model.User;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.UserForm;
import com.beigeoranges.ecms.Utils.RegistrationValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RegistrationValidator registrationValidator;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == UserForm.class) {
            dataBinder.setValidator(registrationValidator);
        }
        // ...
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String viewRegister(Model model) {
        UserForm form = new UserForm();
        model.addAttribute("userForm", form);

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveRegister(Model model,
                               @ModelAttribute("userForm") @Validated UserForm userForm, //
                               BindingResult result, //
                               final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {

            return "registration";
        }
        User newUser = null;
        try {
            newUser = userDao.createUser(userForm);
            System.out.println("did this not work");
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            System.out.println("did this not work");
            return "registration";
        }
        redirectAttributes.addFlashAttribute("flashUser", newUser);

        return "redirect:/login";
    }

}
