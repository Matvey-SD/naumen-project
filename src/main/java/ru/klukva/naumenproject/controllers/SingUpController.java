package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.services.UserRegistrationService;

@Controller
@AllArgsConstructor
public class SingUpController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/signUp")
    public String signUp() {
        return "signup_page";
    }

    @PostMapping("/signUp")
    public String signUpUser(BankUser user) {

        if (userRegistrationService.registerUser(user)) {
            return "redirect:/home";
        }

        return "failed_registration_page";
    }

}
