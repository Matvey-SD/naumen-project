package ru.klukva.naumenproject.models;

import java.util.ArrayList;
import java.util.List;

public interface User {
    public long getUserID();
    public String getFirstName();
    public String getLastName();
    public String getPatronymic();
    public String getFullName();
    public String getEmail();
    public String getPhoneNumber();
    public List<Account> getAccounts();
}
