package com.atns.fullstackatns.registration;

import com.atns.fullstackatns.event.RegistrationCompleteEvent;
import com.atns.fullstackatns.event.listener.RegistrationCompleteEventListener;
import com.atns.fullstackatns.registration.password.IPasswordResetTokenService;
import com.atns.fullstackatns.registration.password.PasswordResetToken;
import com.atns.fullstackatns.registration.password.PasswordResetTokenService;
import com.atns.fullstackatns.registration.token.VerificationToken;
import com.atns.fullstackatns.registration.token.VerificationTokenService;
import com.atns.fullstackatns.user.IUserService;
import com.atns.fullstackatns.user.User;
import com.atns.fullstackatns.utility.UrlUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final IUserService userService;

    private final ApplicationEventPublisher eventPublisher;

    private final VerificationTokenService tokenService;

    private final IPasswordResetTokenService passwordResetTokenService;

    private final RegistrationCompleteEventListener eventListener;


    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new RegistrationRequest());
        return "registration";

    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registration, Model model,
                               HttpServletRequest request) {
        User user = userService.registerUser(registration);

        //TODO publish the verification event here

        System.out.println(registration);
        System.out.println(model);
        //System.out.println(user);
        System.out.println("Password: " + user.getPassword());

        System.out.println("request: " + request.toString());
        System.out.println("request url: " + request.getRequestURL());
        System.out.println("Url Util: " + UrlUtil.getApplicationUrl(request));


        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));


        return "redirect:/registration/registration-form?success"; ////SOS to ("/") πριν το registration


    }


    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {

        Optional<VerificationToken> theToken = tokenService.findbyToken(token);

//        System.out.println("@RequestParam(\"token\"): "+ token);
//        System.out.println(theToken.isPresent());
//        System.out.println(theToken.get().getUser().isEnabled());
//        System.out.println(theToken.get().getExpirationTime());
//        System.out.println(theToken.get().getId());

        if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
            return "redirect:/login?verified";
        }


        System.out.println("String.valueOf(theToken)" + String.valueOf(theToken));

        String verificationResult = tokenService.validateToken(token);
        System.out.println("verificationResult: " + verificationResult);

//        if (verificationResult.equalsIgnoreCase("invalid")) {
//            return "redirect:/error?invalid";
//        } else if (verificationResult.equalsIgnoreCase("expired")) {
//            return "redirect:/error?expired";
//        } else if (verificationResult.equalsIgnoreCase("valid")) {
//            return "redirect:/login?valid";
//
//        }
//        return "redirect:/error?invalid";


        switch (verificationResult.toLowerCase()) {
            case "invalid":
                return "redirect:/error?invalid";
            case "expired":
                return "redirect:/error?expired";
            case "valid":
                return "redirect:/login?valid";
            default:
                return "redirect:/error?invalid";

        }

    }


    @GetMapping("/forgot-password-request")
    public String forgotPassword() {
        return "forgot-password-form";
    }


    @PostMapping("/forgot-password")
    public String resetPassword(HttpServletRequest req, Model model) {

        String email = req.getParameter("email");
        User user = userService.findUserbyEmail(email);
        if (user == null) {
            return "redirect:/registration/forgot-password-request?not_found";
        }

        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);

        //send pass reset Verification email to the user

        String url = UrlUtil.getApplicationUrl(req) + "/registration/password-reset-form?token=" + passwordResetToken;

        try {
            eventListener.sendPasswordResetVerificationEmail(url);
        } catch (MessagingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException e) {

            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/registration/forgot-password-request?success";

    }


    @GetMapping("/password-reset-form")
    public String PasswordResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "password-reset-form";
    }


    @PostMapping("/reset-password")
    public String ResetPassword(HttpServletRequest request, Model model) {
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);


        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/error?invalid_token";
        }
        Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);

        if (theUser.isPresent()) {
            passwordResetTokenService.resetPassword(theUser.get(), password); //user is Optional γιατυο .get()
            return "redirect:/login?reset_success";
        }

        return "redirect:/error?not_found";


    }


}
