package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.AccountsRepository;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountsRepository accountsRepository;

    private final UserService userService;

    public boolean createAccount(BankUser user, String currency) {
        BankAccount account = getNewAccount(user, currency);
        user.getAccounts().add(account);
        userService.saveBankUser(user);
        userService.resynchronize(user);
        return true;
    }

    public boolean existsBankAccountByIdAndUser(Long id, BankUser user) {
        return accountsRepository.existsBankAccountByIdAndUser(id, user);
    }

    public BankAccount getAccountById(Long id) {
        return accountsRepository.getById(id);
    }

    public BankAccount getNewAccount(BankUser user, String currency) {
        return new BankAccount(0D, currency, user);
    }

}
