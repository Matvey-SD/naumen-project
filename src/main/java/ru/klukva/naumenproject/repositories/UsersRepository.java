package ru.klukva.naumenproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klukva.naumenproject.models.BankUser;

public interface UsersRepository extends JpaRepository<BankUser, Long> {
    BankUser findBankUserByEmail(String Email);

    BankUser findBankUserById(Long id);
}
