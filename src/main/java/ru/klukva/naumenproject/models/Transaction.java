package ru.klukva.naumenproject.models;

public interface Transaction {
    public Long getReceiverID();
    public Long getGiverID();
    public Long getReceiverAccountID();
    public Long getGiverAccountID();
    public String getTransactionDateTime();
    public double getTransactionAmount();
}
