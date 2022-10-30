package ru.klukva.naumenproject.models;

import java.time.ZonedDateTime;

public interface Transaction {
    public User getReceiver();
    public User getGiver();
    public Account getReceiverAccount();
    public Account getGiverAccount();
    public ZonedDateTime getTransactionDateTime();
    public double getTransactionAmount();
}
