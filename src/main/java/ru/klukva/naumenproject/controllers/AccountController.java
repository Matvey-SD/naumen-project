package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.UsersRepository;
import ru.klukva.naumenproject.services.AccountRegistrationService;
import ru.klukva.naumenproject.services.UserService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountRegistrationService accountRegistrationService;
    private final UserService userService;

    @GetMapping("/account-registration")
    public String getAccountRegistration() {
        return "account_registration_page";
    }

    @PostMapping("/account-registration")
    public String accountRegistration(@AuthenticationPrincipal BankUser user, String currencyCode) {
        if (!user.isSynchronized()) {
            user = userService.getBankUserByID(user.getId());
            user.setSynchronized(true);
        }
        accountRegistrationService.createAccount(user, currencyCode);
        return "redirect:/home";
    }
}

