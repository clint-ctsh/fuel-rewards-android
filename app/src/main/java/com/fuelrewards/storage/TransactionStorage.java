package com.fuelrewards.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.fuelrewards.models.Transaction;
import com.fuelrewards.models.User;

import java.util.ArrayList;
import java.util.List;

public class TransactionStorage {

    private List<Transaction> transactions = new ArrayList<>();
    private static TransactionStorage transactionStorage;

    protected TransactionStorage() { }

    public static TransactionStorage getInstance() {

        if (transactionStorage == null)
            transactionStorage = new TransactionStorage();

        return transactionStorage;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

