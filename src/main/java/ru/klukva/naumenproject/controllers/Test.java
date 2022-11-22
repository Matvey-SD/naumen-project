package ru.klukva.naumenproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.klukva.naumenproject.models.BankAccount;
import ru.klukva.naumenproject.models.BankUser;

@Controller
public class Test {

    @GetMapping("/testing-trans-history")
    public ResponseEntity<String> test(@AuthenticationPrincipal BankUser user) {
        StringBuilder strB = new StringBuilder();
        for (var account : user.getAccounts()) {
            strB.append("Счет №").append(account.getId()).append("\n");
            for (var transaction : account.getTransactionsHistory()) {
                strB.append(String
                        .format("Номер транзакции: %s\nКому: %s\nОт кого: %s\nНомер счета получателя: %s\nНомер счета отправителя: %s\nСумма транзакции: %s\nДата: %s",
                                transaction.getId(),
                                transaction.getGiverID(),
                                transaction.getReceiverID(),
                                transaction.getGiverAccountID(),
                                transaction.getReceiverAccountID(),
                                transaction.getTransactionAmount(),
                                transaction.getTransactionDateTime()));
            }
        }
        return new ResponseEntity<>(strB.toString(), HttpStatus.ACCEPTED);
    }
}
