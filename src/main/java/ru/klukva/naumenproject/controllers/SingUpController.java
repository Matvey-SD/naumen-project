package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.services.UserService;

@Controller
@AllArgsConstructor
public class SingUpController {

    private final UserService userService;

    @GetMapping("/signUp")
    public String signUp() {
        return "signup_page";
    }

    @PostMapping("/signUp")
    public String signUpUser(BankUser user) {
        boolean userIsRegistered = userService.registerUser(user);
        return userIsRegistered ? "redirect:/home" : "failed_registration_page";
    }

}
