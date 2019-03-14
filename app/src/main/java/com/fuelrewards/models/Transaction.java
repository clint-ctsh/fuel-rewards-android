package com.fuelrewards.models;

import java.sql.Date;

public class Transaction {

    private Long id;
    private Date transactionDate;
    private Double transactionAmount;
    private String cardNumber;

    public Transaction(Long id, Date transactionDate, Double transactionAmount, String cardNumber) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
