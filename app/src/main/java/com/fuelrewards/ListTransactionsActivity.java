package com.fuelrewards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fuelrewards.adapters.TransactionAdapter;
import com.fuelrewards.exceptions.NetworkException;
import com.fuelrewards.models.Transaction;
import com.fuelrewards.storage.TransactionStorage;

import java.util.ArrayList;
import java.util.List;

public class ListTransactionsActivity extends AppCompatActivity {

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transactions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



      //  TextView emptyText = (TextView)findViewById(android.R.id.empty);
        ListView listView = (ListView) findViewById(R.id.all_transactions);
       // listView.setEmptyView(emptyText);

        Thread thread = new Thread() {
            @Override
            public void run() {

                final List<String> allTrans = updateTransactionAdapter();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listView = (ListView) findViewById(R.id.all_transactions);

                        String[] transactionArr = new String[allTrans.size()];
                        transactionArr = allTrans.toArray(transactionArr);
                        adapter = new ArrayAdapter<String>(ListTransactionsActivity.this,
                                R.layout.activity_listview, transactionArr);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        thread.start();
    }

    private List<String> updateTransactionAdapter() {

        List<String> allTransactionsStringified = new ArrayList<>();
        try {
            List<Transaction> allTransactions = TransactionAdapter
                    .getInstance().getAllTransactions();


            for (Transaction t : allTransactions) {
                System.out.println("List: " + t.getTransactionName() + " | " + t.getTransactionDate() + " | $" + t.getTransactionAmount());
                allTransactionsStringified.add(t.getTransactionName() + " | " + t.getTransactionDate() + " | $" + t.getTransactionAmount());
            }
        } catch (NetworkException e) {
            System.out.println("Unable to load transactions");
            e.printStackTrace();
        }

        return allTransactionsStringified;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(ListTransactionsActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
