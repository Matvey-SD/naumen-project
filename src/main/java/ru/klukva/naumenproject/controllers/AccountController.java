package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.AccountsRepository;
import ru.klukva.naumenproject.services.AccountRegistrationService;
import ru.klukva.naumenproject.services.AccountService;
import ru.klukva.naumenproject.services.UserService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountRegistrationService accountRegistrationService;

    private final UserService userService;

    private final AccountService accountService;

    @GetMapping("/account-registration")
    public String getAccountRegistration() {
        return "account_registration_page";
    }

    @PostMapping("/account-registration")
    public String accountRegistration(@AuthenticationPrincipal BankUser user, String currencyCode) {
        accountRegistrationService.createAccount(user, currencyCode);
        return "redirect:/home";
    }

    @GetMapping("/account")
    public String getAccountInfo(Long id, @AuthenticationPrincipal BankUser user, Model model) {
        if (accountService.existsBankAccountByIdAndUser(id, user)) {
            BankAccount account = accountService.getAccountById(id);
            model.addAttribute("BankAccount", account);
            return "account_info_page";
        }

        return "redirect:/home";
    }
}

