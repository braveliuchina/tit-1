package org.tit_admin_control.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tit_admin_common.core.Validity;
import org.tit_admin_model.core.entity.request.UserRO;

import java.util.Locale;

/**
 * The Merchant entity registration and related pages
 * handler
 *
 * @author: 刘晓勇
 */
@Controller
public class UserController extends BaseApiController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    //private @Autowired UserService userService;
    //private @Autowired MailSenderActor mailSenderActor;


    /**
     * Show registration page
     *
     * @param locale
     * @param model
     *
     * @return
     */
    @RequestMapping(value = "userRegistration", method = RequestMethod.GET)
    public String userRegistration(Locale locale, Model model) {
        return "userRegistration";
    }


    /**
     * Login a merchant
     *
     * @param locale
     * @param model
     * @param userRO
     *
     * @return
     */
  /*  @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public String loginMerchant(Locale locale, Model model,
                                @ModelAttribute("loginUserForm") UserRO userRO) {
        try {
            // Authenticate the credentials
            if ((userRO.username != null && userRO.username.trim().length() > 0)
                    && (userRO.password != null && userRO.password.trim().length() > 0)) {
                final User user = userService.findByUsername(userRO.username);
                log.info("User Found: " + user.getUserName());

                if (user.getPassWord().equals(User.hashPassword(userRO.password))) {
                    log.info("Authenticated: " + user.getUserName());

                    // Update the login count and other info
                    userService.loginUser(user, request);

                    // Store the user in session
                    request.getSession(true).setAttribute(Key.userInSession, user);
                    model.addAttribute("ls", true);
                    return Key.redirect + Route.dashboard;
                } else {
                    log.info("User Authentication Failed: " + user.getUserName());
                    throw new AuthenticationFailedException(msg.aFailed, msg.aFailedCode);
                }
            } else {
                throw new AuthCredentialsMissingException(msg.aParamMissing, msg.aParamMissingCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            addError(msg.loginError, model);
            addError(e.getMessage(), model);
            return View.home;
        }
    }


    *//**
     * Registers new users
     *
     * @param locale
     * @param model
     * @param userRO
     *
     * @return
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public String registerUser(Locale locale, Model model,
                                   @ModelAttribute("registerUserForm") UserRO userRO) {
        try {
            User u = userRO.user(props);
            Validity validity = userService.validate(u);
            if (validity.isValid()) {
                userService.registerUser(u, request);
                mailSenderActor.sendUserEmailIdConfirmationMail(u);
                request.getSession(true).setAttribute(Key.userInSession, u);
                model.addAttribute(Key.isRegister, true);
                return Key.redirect + Route.dashboard;
            } else {
                addError(msg.registerError, model);
                return View.userRegistration;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            addError(msg.registerError, model);
            addError(e.getMessage(), model);
            return View.userRegistration;
        }
    }


    /**
     * Logs out a merchant by deleting the session
     *
     * @param locale
     * @param model
     *
     * @return
     *//*
    @RequestMapping(value = Route.logoutUser, method = RequestMethod.GET)
    public String logoutUser(Locale locale, Model model) {
        request.getSession().removeAttribute(Key.userInSession);
        return "redirect:" + Route.home;
    }*/
}