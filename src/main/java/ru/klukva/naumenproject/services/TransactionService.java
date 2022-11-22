package ru.klukva.naumenproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klukva.naumenproject.dto.BankTransactionDTO;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankTransaction;
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

    public void makeTransaction(BankTransactionDTO transactionDTO) {

        String transactionType = transactionDTO.getTransactionType();
        BankAccount giver = null;
        BankAccount receiver = null;
        switch (transactionType) {
            case "transf":
                giver = accountsRepository.findBankAccountById(transactionDTO.getGiverAccountID());
                receiver = accountsRepository.findBankAccountById(transactionDTO.getReceiverAccountID());
                makeTransfer(
                        giver,
                        receiver,
                        transactionDTO.getTransactionAmount());
                break;
            case "add":
                giver = accountsRepository.findBankAccountById(transactionDTO.getGiverAccountID());
                receiver = giver;
                makeAdding(
                        giver,
                        transactionDTO.getTransactionAmount());
                break;
            case "withd":
                giver = accountsRepository.findBankAccountById(transactionDTO.getGiverAccountID());
                receiver = giver;
                makeWithdrawal(
                        giver,
                        transactionDTO.getTransactionAmount());
                break;
            default:
        }

        if (giver == null && receiver == null) throw new NullPointerException("Transaction must not be null");

        BankTransaction transaction = createTransaction(receiver, giver, transactionDTO.getTransactionAmount());
        if (giver == receiver) {
            transaction.getTransactionParticipants().add(giver);
        } else {
            transaction.getTransactionParticipants().add(giver);
            transaction.getTransactionParticipants().add(receiver);
        }
        transactionsRepository.save(transaction);
    }

    private void makeTransfer(BankAccount giver, BankAccount receiver, double amount) {
        makeWithdrawal(giver, amount);
        makeAdding(receiver, amount);
    }

    private void makeWithdrawal(BankAccount account, double amount) {
        account.setBalance(account.getBalance() - amount);
    }

    private void makeAdding(BankAccount account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    public BankTransaction createTransaction(BankAccount receiver, BankAccount giver, double amount) {

        return new BankTransaction(
                receiver.getUser().getId(),
                giver.getUser().getId(),
                receiver.getId(),
                giver.getId(),
                getTransactionDateTimeString(),
                amount
        );
    }


    public String getTransactionDateTimeString() {
        ZonedDateTime transactionDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        return DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(transactionDateTime);
    }


   /* public boolean tryIncreaseBalance(double value) {
        double tempBalance = balance + value;

        if (BALANCE_LIMIT < tempBalance) {
            return false;
        }
        balance = tempBalance;
        return true;
    }

    public boolean tryDecreaseBalance(double value) {
        double tempBalance = balance - value;

        if (tempBalance < 0) {
            return false;
        }

        balance = tempBalance;
        return true;
    }*/

}
