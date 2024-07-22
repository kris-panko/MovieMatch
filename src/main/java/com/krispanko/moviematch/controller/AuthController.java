package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling authentication-related requests.
 */
@Controller
public class AuthController {

    private final UserService userService;

    /**
     * Constructs the AuthController with the specified userService.
     *
     * @param userService the user service
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET requests to the login page.
     *
     * @return the name of the login view
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles GET requests to the signup page.
     *
     * @param model the model to be used in the view
     * @return the name of the signup view
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    /**
     * Handles POST requests for user registration.
     *
     * @param user the user to be registered
     * @param model the model to be used in the view
     * @return the name of the view to be redirected to
     */
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already exists.");
            return "signup";
        }
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already exists.");
            return "signup";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
}
