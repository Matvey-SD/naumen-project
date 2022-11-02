package ru.klukva.naumenproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klukva.naumenproject.models.BankAccount;

public interface AccountsRepository extends JpaRepository<BankAccount, Long> {
}
