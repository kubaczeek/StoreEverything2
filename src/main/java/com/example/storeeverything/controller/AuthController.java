
package com.example.storeeverything.controller;

import com.example.storeeverything.dto.UserDto;
import com.example.storeeverything.model.User;
import com.example.storeeverything.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/index"})
    public String home(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        return "index";
    }

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @GetMapping({"/register"})
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping({"/register"})
    public String registration(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        } else {
            User existingUser = this.userService.findUserByName(userDto.getName());
            if (existingUser != null) {
                result.rejectValue("name", (String)null, "There is already an account registered with the same name");
                model.addAttribute("user", userDto);
                return "/register";
            } else {
                this.userService.saveUser(userDto);
                return "redirect:/register?success";
            }
        }
    }
}
