package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;



@Service
@AllArgsConstructor
public class UserRegistrationService {
    private final UserService userService;

    public boolean registerUser(BankUser user) {
        userService.addUser(user);
        return true;
    }

}
