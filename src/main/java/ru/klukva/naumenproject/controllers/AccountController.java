package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.klukva.naumenproject.dto.BankTransactionDTO;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.services.AccountService;
import ru.klukva.naumenproject.services.TransactionService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    @GetMapping("/account-registration")
    public String getAccountRegistration() {
        return "account_registration_page";
    }

    @PostMapping("/account-registration")
    public String accountRegistration(@AuthenticationPrincipal BankUser user, String currencyCode) {
        accountService.createAccount(user, currencyCode);
        return "redirect:/home";
    }

    @GetMapping("/account")
    public String getAccountInfo(@AuthenticationPrincipal BankUser user, Long id, Model model) {
        if (!accountService.existsBankAccountByIdAndUser(id, user))
            return "redirect:/home";

        BankAccount account = accountService.getAccountById(id);
        model.addAttribute("BankAccount", account);
        return "account_info_page";
    }

    @GetMapping("/transaction")
    public String getTransactionForm(@AuthenticationPrincipal BankUser user,
                                     Long id,
                                     String transactionType,
                                     Model model) {

        if (!accountService.existsBankAccountByIdAndUser(id, user))
            return "redirect:/home";

        BankAccount account = accountService.getAccountById(id);
        model.addAttribute("BankAccount", account);
        model.addAttribute("transactionType", transactionType);
        return "transaction_account_page";
    }

    @PostMapping("/transaction")
    public String makeTransaction(BankTransactionDTO transactionDTO) {
        transactionService.makeTransaction(transactionDTO);
        return "redirect:/account?id=" + (transactionDTO.getGiverAccountID() == null
                ? transactionDTO.getReceiverAccountID()
                : transactionDTO.getGiverAccountID());
    }

    @GetMapping("/get-transaction-history")
    public String getTransactionHistory(@AuthenticationPrincipal BankUser user, Long id, Model model) {
        if (!accountService.existsBankAccountByIdAndUser(id, user))
            return "redirect:/home";
        
        BankAccount account = accountService.getAccountById(id);
        model.addAttribute("BankAccount", account);
        return "transactions_history_page";
    }
}

