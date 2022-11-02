package ru.klukva.naumenproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.klukva.naumenproject.models.Account;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankTransaction;
import ru.klukva.naumenproject.models.BankUser;

import java.util.ArrayList;

@SpringBootApplication
public class NaumenProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(NaumenProjectApplication.class, args);
    }

}
