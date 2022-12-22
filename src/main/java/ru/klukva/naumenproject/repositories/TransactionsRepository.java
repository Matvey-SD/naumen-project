package ru.klukva.naumenproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klukva.naumenproject.models.BankTransaction;

public interface TransactionsRepository extends JpaRepository<BankTransaction, Long> {
    boolean existsByGiverIDAndIdOrReceiverIDAndId(Long giverID, Long id, Long receiverID, Long id2);

    BankTransaction getBankTransactionById(Long id);
}
