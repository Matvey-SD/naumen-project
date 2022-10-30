package ru.klukva.naumenproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class BankUser implements User {
    private long userID;
    @Setter private String firstName;
    @Setter private String lastName;
    @Setter private String patronymic;
    @Setter private String email;
    @Setter private String phoneNumber;
    private final List<Account> accounts = new ArrayList<>();

    @Override
    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }
}