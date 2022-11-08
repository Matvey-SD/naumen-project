package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;

import ru.klukva.naumenproject.repositories.UsersRepository;

@Service
@AllArgsConstructor
public class AccountRegistrationService {

    private final UsersRepository usersRepository;

    public boolean createAccount(BankUser user, String currency) {
        var a = new BankAccount(0D, currency, user);
        user.getAccounts().add(a);
        usersRepository.save(user);
        user.setSynchronized(false);
        return true;
    }

}
