package com.fuelrewards.adapters;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.fuelrewards.common.Constants;
import com.fuelrewards.exceptions.NetworkException;
import com.fuelrewards.models.Transaction;
import com.fuelrewards.models.User;
import com.fuelrewards.storage.TransactionStorage;
import com.fuelrewards.storage.UserStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionAdapter {

    private static TransactionAdapter transactionAdapter;
    private RequestQueue queue;

    protected TransactionAdapter(Activity activity) {
        queue = Volley.newRequestQueue(activity);
    }

    public static void initialize(Activity activity) {
        transactionAdapter = new TransactionAdapter(activity);
    }

    public static TransactionAdapter getInstance() {
        return transactionAdapter;
    }

    public Transaction submitTransaction(String name, double amount) throws NetworkException {
        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("name", name);
            jsonRequest.put("amount", amount);

            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Constants.SUBMIT_TRANSACTION_URL, jsonRequest, future, future)
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    // set application/json header
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };

            queue.add(jsonObjectRequest);
            JSONObject response = future.get();

            Long transactionId = response.getLong("id");
            String transactionDate = response.getString("date");

            return new Transaction(transactionId, transactionDate, name, amount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NetworkException("Error during submit transaction", e);
        }
    }

    public List<Transaction> getAllTransactions() throws NetworkException {
        try {
            RequestFuture<JSONArray> future = RequestFuture.newFuture();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, Constants.GET_ALL_TRANSACTIONS_URL, null, future, future)
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    // set application/json header
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };

            queue.add(jsonArrayRequest);

            JSONArray jsonTransactions = future.get();
            List<Transaction> transactions = new ArrayList<>();

            for (int i = 0; i < jsonTransactions.length(); i++) {

                JSONObject transactionJSON = jsonTransactions.getJSONObject(i);

                Long id = transactionJSON.getLong("id");
                String date = transactionJSON.getString("date");
                Double amount = transactionJSON.getDouble("amount");
                String name = transactionJSON.getString("name");

                Transaction t = new Transaction(id, date, name, amount);
                transactions.add(t);
            }

            TransactionStorage.getInstance().setTransactions(transactions);
            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NetworkException("Error during get all transactions", e);
        }
    }
}
