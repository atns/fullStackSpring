package com.atns.fullstackatns.registration;

import com.atns.fullstackatns.event.RegistrationCompleteEvent;
import com.atns.fullstackatns.registration.token.VerificationRepository;
import com.atns.fullstackatns.registration.token.VerificationToken;
import com.atns.fullstackatns.registration.token.VerificationTokenService;
import com.atns.fullstackatns.user.IUserService;
import com.atns.fullstackatns.user.User;
import com.atns.fullstackatns.utility.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final IUserService userService;

    private final ApplicationEventPublisher eventPublisher;

    private final VerificationTokenService tokenService;


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
        System.out.println(user);
        System.out.println(user.getPassword());


        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));
        return "redirect:/registration/registration-form?success"; ////SOS to ("/") πριν το registration


    }


    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        Optional<VerificationToken> theToken = tokenService.findbyToken(token);
        if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(String.valueOf(theToken));

        if (verificationResult.equalsIgnoreCase("invalid")) {
            return "redirect:/error?invalid";
        } else if (verificationResult.equalsIgnoreCase("expired")) {
            return "redirect:/error?expired";
        } else if (verificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/login?valid";

        }
        return "redirect:/error?invalid";
    }




}
