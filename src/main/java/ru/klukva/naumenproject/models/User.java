package ru.klukva.naumenproject.models;

import java.util.List;

public interface User {
    public Long getId();
    public String getFirstName();
    public String getLastName();
    public String getPatronymic();
    public String getFullName();
    public String getEmail();
    public String getPhoneNumber();
    public List<BankAccount> getAccounts();
}
