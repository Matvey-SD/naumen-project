package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.AccountsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountsRepository accountsRepository;

    private final UserService userService;

    public boolean createAccount(BankUser user, String currency) {
        BankAccount account = getNewAccount(user, currency);
        accountsRepository.save(account);
        return true;
    }

    public BankAccount getNewAccount(BankUser user, String currency) {
        return new BankAccount(0D, currency, user);
    }

    public List<BankAccount> getAllBankUserAccounts(BankUser user) {
        return accountsRepository.findAllByUser(user);
    }

    public BankAccount getAccountById(Long id) {
        return accountsRepository.getById(id);
    }

    public boolean existsBankAccountByIdAndUser(Long id, BankUser user) {
        return accountsRepository.existsBankAccountByIdAndUser(id, user);
    }
}
