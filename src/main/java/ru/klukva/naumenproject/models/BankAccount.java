package ru.klukva.naumenproject.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankAccount implements Account {
    @Transient
    private static final double BALANCE_LIMIT = 1_000_000d;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double balance;
    private String currencyName;
    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private BankUser user;

    @ManyToMany
    @JoinTable(
            name = "account_transactions",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "transaction_id")}
    )
    private final List<BankTransaction> transactionsHistory = new ArrayList<>();

    public boolean tryIncreaseBalance(double value) {
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
    }

}
