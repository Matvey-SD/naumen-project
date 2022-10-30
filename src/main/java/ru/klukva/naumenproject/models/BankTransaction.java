package ru.klukva.naumenproject.models;

import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class BankTransaction implements Transaction {
    private final User receiver;
    private final User giver;
    private final Account receiverAccount;
    private final Account giverAccount;
    private final ZonedDateTime transactionDateTime;
    private final double transactionAmount;

    public BankTransaction(User receiver, User giver, Account receiverAccount, Account giverAccount, double transactionAmount){
        transactionDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        this.receiver = receiver;
        this.giver = giver;
        this.receiverAccount = receiverAccount;
        this.giverAccount = giverAccount;
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDateTimeString() {
        return DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(transactionDateTime);
    }
}
