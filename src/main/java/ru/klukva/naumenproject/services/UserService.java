package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.UsersRepository;

import javax.transaction.Transactional;
import java.util.List;

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
        saveBankUser(user);
    }

    public void saveBankUser(BankUser user) {
        usersRepository.save(user);
    }
}

