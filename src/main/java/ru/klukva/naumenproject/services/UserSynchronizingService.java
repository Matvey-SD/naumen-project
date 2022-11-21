package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankUser;

@Service
@AllArgsConstructor
public class UserSynchronizingService {

    private static UserService userService;

    public static BankUser synchronize(BankUser user) {

        if (user == null) throw new NullPointerException("User can't be null");

        if (!user.isSynchronized()) {
            user = userService.getBankUserByID(user.getId());
            user.setSynchronized(true);
        }

        return user;
    }

    public static void resynchronize(BankUser user) {
        user.setSynchronized(false);
    }

}
