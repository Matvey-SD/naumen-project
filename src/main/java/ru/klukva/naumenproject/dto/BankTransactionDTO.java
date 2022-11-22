package ru.klukva.naumenproject.dto;

import lombok.Data;

@Data
public class BankTransactionDTO {

    private Long receiverAccountID;

    private Long giverAccountID;

    private double transactionAmount;

    /*
    add - пополнение счета
    transf - перевод другому клиенту
    withd - снятие со счета
     */
    private String transactionType;
}
