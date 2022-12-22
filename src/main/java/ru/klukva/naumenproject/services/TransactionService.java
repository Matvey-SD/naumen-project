package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.dto.BankTransactionDTO;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankTransaction;
import ru.klukva.naumenproject.models.BankUser;
import ru.klukva.naumenproject.repositories.AccountsRepository;
import ru.klukva.naumenproject.repositories.TransactionsRepository;
import ru.klukva.naumenproject.repositories.UsersRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionsRepository transactionsRepository;

    private AccountsRepository accountsRepository;

    private UsersRepository usersRepository;

    private ConvertorService convertorService;

    public void makeTransaction(BankTransactionDTO transactionDTO) {
        System.out.println(transactionDTO.getReceiverAccountID());
        System.out.println(transactionDTO.getGiverAccountID());
        String transactionType = transactionDTO.getTransactionType();
        BankAccount giver = null;
        BankAccount receiver = null;
        double enrollAmount = transactionDTO.getTransactionAmount();
        switch (transactionType) {
            case "transf":
                giver = accountsRepository.findBankAccountById(transactionDTO.getGiverAccountID());
                receiver = accountsRepository.findBankAccountById(transactionDTO.getReceiverAccountID());
                if (!giver.getCurrencyCode().equals(receiver.getCurrencyCode()))
                    enrollAmount = convertorService.getConvertedValue(enrollAmount, giver.getCurrencyCode(), receiver.getCurrencyCode());
                makeTransfer(
                        giver,
                        receiver,
                        transactionDTO.getTransactionAmount(),
                        enrollAmount);
                break;
            case "add":
                receiver = accountsRepository.findBankAccountById(transactionDTO.getReceiverAccountID());
                makeAdding(
                        receiver,
                        transactionDTO.getTransactionAmount());
                break;
            case "withd":
                giver = accountsRepository.findBankAccountById(transactionDTO.getGiverAccountID());
                makeWithdrawal(
                        giver,
                        transactionDTO.getTransactionAmount());
                break;
            default:
        }

        BankTransaction transaction = createTransaction(receiver, giver, transactionDTO.getTransactionAmount(), enrollAmount);
        if (!(giver == null))
            transaction.getTransactionParticipants().add(giver);

        if (!(receiver == null))
            transaction.getTransactionParticipants().add(receiver);

        transactionsRepository.save(transaction);
    }

    private void makeTransfer(BankAccount giver, BankAccount receiver, double withdrawAmount, double enrollAmount) {
        makeWithdrawal(giver, withdrawAmount);
        makeAdding(receiver, enrollAmount);
    }

    private void makeWithdrawal(BankAccount account, double amount) {
        account.setBalance(account.getBalance() - amount);
    }

    private void makeAdding(BankAccount account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    public BankTransaction createTransaction(BankAccount receiver, BankAccount giver, double sendAmount, double receiveAmount) {

        return new BankTransaction(
                receiver == null ? null : receiver.getUser().getId(),
                giver == null ? null : giver.getUser().getId(),
                receiver == null ? null : receiver.getId(),
                giver == null ? null : giver.getId(),
                getTransactionDateTimeString(),
                sendAmount,
                receiveAmount
        );
    }


    public String getTransactionDateTimeString() {
        ZonedDateTime transactionDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        return DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(transactionDateTime);
    }

    public boolean existsTransactionByIdAndUser(Long id, BankUser user) {
        return transactionsRepository.existsByGiverIDAndIdOrReceiverIDAndId(user.getId(), id, user.getId(), id);
    }

    public BankTransaction getTransactionById(Long id) {
        return transactionsRepository.getBankTransactionById(id);
    }
}
