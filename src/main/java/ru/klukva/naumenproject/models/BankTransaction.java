package ru.klukva.naumenproject.models;

import lombok.Getter;

import java.util.Date;

@Getter
public class BankTransaction implements Transaction {
    private User receiver;
    private User giver;
    private Account receiverAccount;
    private Account giverAccount;
    private Date transactionTime;
    private Double transactionAmount;
}
