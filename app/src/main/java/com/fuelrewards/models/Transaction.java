package com.fuelrewards.models;

import java.sql.Date;

public class Transaction {

    private Long id;
    private String transactionDate;
    private String transactionName;
    private Double transactionAmount;

    public Transaction(Long id, String transactionDate, String transactionName,
                       Double transactionAmount) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }
}
