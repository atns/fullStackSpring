package com.atns.fullstackatns.registration;

import com.atns.fullstackatns.event.RegistrationCompleteEvent;
import com.atns.fullstackatns.user.IUserService;
import com.atns.fullstackatns.user.User;
import com.atns.fullstackatns.utility.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final IUserService userService;

    private final ApplicationEventPublisher eventPublisher;


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

}
