package ru.klukva.naumenproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.klukva.naumenproject.dto.BankTransactionDTO;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankTransaction;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.services.AccountService;
import ru.klukva.naumenproject.services.TransactionService;
import ru.klukva.naumenproject.services.UserService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserService userService;


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

    @GetMapping("/get-transaction-info")
    public String getTransactionInfo(@AuthenticationPrincipal BankUser user, Long id, Long accountId, Model model) {
        if (!transactionService.existsTransactionByIdAndUser(id, user))
            return "redirect:/home";
        BankTransaction transaction = transactionService.getTransactionById(id);
        model.addAttribute("transaction", transaction);

        BankUser otherUser = user;
        BankAccount account = accountService.getAccountById(accountId);
        BankAccount otherAccount = account;
        if (transaction.getGiverAccountID() != null && !transaction.getGiverAccountID().equals(account.getId())) {
            otherUser = userService.getBankUserByID(transaction.getGiverID());
            otherAccount = accountService.getAccountById(transaction.getGiverAccountID());
        }
        if (transaction.getReceiverAccountID() != null && !transaction.getReceiverAccountID().equals(account.getId())) {
            otherUser = userService.getBankUserByID(transaction.getReceiverID());
            otherAccount = accountService.getAccountById(transaction.getReceiverAccountID());
        }

        model.addAttribute("user", user);
        model.addAttribute("otherUser", otherUser);
        model.addAttribute("bankAccount", account);
        model.addAttribute("otherBankAccount", otherAccount);
        return "/trans_info";
    }
}

