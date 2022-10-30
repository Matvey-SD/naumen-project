package ru.klukva.naumenproject.models;

import java.util.Date;

public interface Transaction {
    public User getReceiver();
    public User getGiver();
    public Account getReceiverAccount();
    public Account getGiverAccount();
    public Date getTransactionTime();
    public double getTransactionAmount();
}
