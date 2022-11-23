package ru.klukva.naumenproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    private final List<BankAccount> transactionParticipants = new ArrayList<>();

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
