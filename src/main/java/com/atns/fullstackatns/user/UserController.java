package com.atns.fullstackatns.user;

import com.atns.fullstackatns.registration.token.VerificationRepository;
import com.atns.fullstackatns.registration.token.VerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final VerificationRepository verificationRepository;
    private final VerificationTokenService verificationTokenService;

    @GetMapping
    public String getUsers(Model model) {
        // model.addAttribute("users",userService.getAllUsers());
        model.addAttribute("users", userService.getAllEnabledUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "update-user";
        } else {
            // Handle the case where the user is not found
            // Redirecting to an error page or showing a custom message can be done here
            return "redirect:/error"; // Assuming there's an error page mapped to this path
        }
    }


    @Transactional
    @PostMapping("update/{id}")
    public String updateUser(@PathVariable("id") Long id, User user) {
        userService.updateUser(id, user.getFirstName(), user.getLastName(), user.getEmail());
        return "redirect:/users?update_success";

    }

    @Transactional
    @PostMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users?delete_success";
    }
}
