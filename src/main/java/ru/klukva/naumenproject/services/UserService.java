package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.UsersRepository;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = usersRepository.findBankUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    public boolean registerUser(BankUser user) {
        boolean userAlreadyExist = usersRepository.existsBankUserByEmail(user.getEmail());
        if (!userAlreadyExist) {
            addUser(user);
            return true;
        }
        return false;
    }

    public BankUser getBankUserByID(Long id) {
        return usersRepository.findBankUserById(id);
    }
    public void addUser(BankUser user) {
        user.setHashPassword(passwordEncoder.encode(user.getDecodePassword()));
        usersRepository.save(user);
    }

    public void saveBankUser(BankUser user) {
        usersRepository.save(user);
    }

    public BankUser synchronize(BankUser user) {

        if (user == null) throw new NullPointerException("User can't be null");

        if (!user.isSynchronized()) {
            user = getBankUserByID(user.getId());
            user.setSynchronized(true);
        }

        return user;
    }

    public static void resynchronize(BankUser user) {
        user.setSynchronized(false);
    }
}

