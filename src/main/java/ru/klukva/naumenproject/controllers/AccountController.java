package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.AccountsRepository;
import ru.klukva.naumenproject.repositories.UsersRepository;
import ru.klukva.naumenproject.services.AccountRegistrationService;
import ru.klukva.naumenproject.services.UserService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountRegistrationService accountRegistrationService;
    private final UserService userService;

    private final AccountsRepository accountsRepository;

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

    @GetMapping("/account")
    public String getAccountInfo(Long id, @AuthenticationPrincipal BankUser user, Model model) {
        if (accountsRepository.existsBankAccountByIdAndUser(id, user)) {
            BankAccount account = accountsRepository.getById(id);
            model.addAttribute("BankAccount", account);
            return "account_info_page";
        }

        return "redirect:/home";
    }
}

