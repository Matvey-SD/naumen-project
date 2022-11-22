package ru.klukva.naumenproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction implements Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverID;

    private Long giverID;

    private Long receiverAccountID;

    private Long giverAccountID;

    private String transactionDateTime;

    private double transactionAmount;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_transactions",
            joinColumns = {@JoinColumn(name = "transaction_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_id")}
    )
    private List<BankAccount> transactionParticipants = new ArrayList<>();

    public BankTransaction(Long receiverID,
                           Long giverID,
                           Long receiverAccountID,
                           Long giverAccountID,
                           String transactionDateTimeString,
                           double transactionAmount) {

        this.receiverID = receiverID;
        this.giverID = giverID;
        this.receiverAccountID = receiverAccountID;
        this.giverAccountID = giverAccountID;
        this.transactionDateTime = transactionDateTimeString;
        this.transactionAmount = transactionAmount;

    }
}
