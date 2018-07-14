package com.beigeoranges.ecms.Utils;

import org.apache.commons.validator.routines.EmailValidator;
import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.UserForm;
import com.beigeoranges.ecms.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();

    String adminCode = "admincode";

    @Autowired
    private UserDao userDao;

    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;

        // Check the fields of UserForm and cross-reference errors with validation.properties
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.UserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.UserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.UserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.UserForm.confirmPassword");

        if (!this.emailValidator.isValid(userForm.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "Pattern.UserForm.email");
        }
        if (!userForm.getAdminCode().equals("") && !userForm.getAdminCode().equals(adminCode) ) {
            // Invalid admin code.
            errors.rejectValue("adminCode", "Pattern.UserForm.adminCode");

        }else if (userForm.getUserId() == null) {
            User dbUser = userDao.findUserAccount(userForm.getEmail());
            if (dbUser != null) {
                // Email has been used by another account.
                errors.rejectValue("email", "Duplicate.UserForm.email");
            }
        }if (!errors.hasErrors()) {
            if (!userForm.getConfirmPassword().equals(userForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.UserForm.confirmPassword");
            }
        }
    }

}