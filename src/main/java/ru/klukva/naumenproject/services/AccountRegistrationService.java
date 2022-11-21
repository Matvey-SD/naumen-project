package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;

import ru.klukva.naumenproject.repositories.UsersRepository;

@Service
@AllArgsConstructor
public class AccountRegistrationService {

    private final UserService userService;

    private final AccountService accountService;

    public boolean createAccount(BankUser user, String currency) {
        user = UserSynchronizingService.synchronize(user);
        BankAccount account = accountService.getNewAccount(user,currency);
        user.getAccounts().add(account);
        userService.saveBankUser(user);
        UserSynchronizingService.resynchronize(user);
        return true;
    }

}
