package com.atns.fullstackatns.registration;

import com.atns.fullstackatns.event.RegistrationCompleteEvent;
import com.atns.fullstackatns.user.IUserService;
import com.atns.fullstackatns.user.User;
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
    public String registerUser(@ModelAttribute("user") RegistrationRequest registration, Model model) {
        User user = userService.registerUser(registration);
        //TODO publish the verification event here


        eventPublisher.publishEvent(new RegistrationCompleteEvent(user,""));
        return "redirect:registration//registration-form?success";

    }

}
