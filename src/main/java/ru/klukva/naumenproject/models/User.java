package ru.klukva.naumenproject.models;

import java.util.List;

public interface User {
    public String getFirstName();
    public String getLastName();
    public String getPatronymic();
    public String getEmail();
    public String getPhoneNumber();
    public List<Account> getAccounts();
}
