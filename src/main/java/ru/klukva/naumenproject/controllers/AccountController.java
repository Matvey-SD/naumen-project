package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.UsersRepository;
import ru.klukva.naumenproject.services.AccountRegistrationService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountRegistrationService accountRegistrationService;

    private final UsersRepository usersRepository;
    @GetMapping("/account-registration")
    public String getAccountRegistration() {
        return "account_registration_page";
    }

    @PostMapping("/account-registration")
    public String accountRegistration(@AuthenticationPrincipal BankUser user, String currencyCode) {
        user = usersRepository.findBankUserById(user.getId());
        accountRegistrationService.createAccount(user, currencyCode);
        return "redirect:/home";
    }
}

