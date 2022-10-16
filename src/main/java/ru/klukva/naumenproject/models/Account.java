package ru.klukva.naumenproject.models;

import java.util.List;

public interface Account {
    public long getAccountID();
    public double getBalance();
    public String getCurrencyName();
    public String getCurrencyCode();
    public List<Transaction> getTransactionsHistory();
}
