package ru.klukva.naumenproject.controllers;


import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.services.AccountService;
import ru.klukva.naumenproject.services.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class MainController {

    private final AccountService accountService;

    @GetMapping("/")
    public String getStart() {
        return "start_page";
    }

    @GetMapping("/home")
    public String getHome(@AuthenticationPrincipal BankUser user, Model model) {
        List<BankAccount> userAccounts = accountService.getAllBankUserAccounts(user);
        model.addAttribute("user", user);
        model.addAttribute("userAccounts", userAccounts);
        return "home_page";
    }
}
