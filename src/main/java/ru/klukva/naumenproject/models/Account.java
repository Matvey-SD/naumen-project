package ru.klukva.naumenproject.models;

import java.util.List;

public interface Account {
    public Long getId();
    public Double getBalance();
    public String getCurrencyCode();
    public List<BankTransaction> getTransactionsHistory();
}
