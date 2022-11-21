package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.UsersRepository;


@Service
@AllArgsConstructor
public class UserRegistrationService {
    private final UserService userService;
    private final UsersRepository usersRepository;

    public boolean registerUser(BankUser user) {
        boolean userAlreadyExist = usersRepository.existsBankUserByEmail(user.getEmail());
        if (!userAlreadyExist) {
            userService.addUser(user);
            return true;
        }
        return false;
    }
}
