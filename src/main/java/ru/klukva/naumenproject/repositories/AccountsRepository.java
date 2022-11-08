package ru.klukva.naumenproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;

public interface AccountsRepository extends JpaRepository<BankAccount, Long> {
    boolean existsBankAccountByIdAndUser(Long id, BankUser user);

}
