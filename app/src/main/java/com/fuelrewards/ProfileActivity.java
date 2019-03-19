package com.fuelrewards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.fuelrewards.common.Constants;
import com.fuelrewards.models.Transaction;
import com.fuelrewards.models.User;
import com.fuelrewards.storage.TransactionStorage;
import com.fuelrewards.storage.UserStorage;

import java.text.DecimalFormat;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        User user = UserStorage.getInstance().getUser();

        TextView mFirstNameText = (TextView) findViewById(R.id.first_name_text);
        mFirstNameText.setText(user.getFirstName());

        TextView mLastNameText = (TextView) findViewById(R.id.last_name_text);
        mLastNameText.setText(user.getLastName());

        TextView mEmailText = (TextView) findViewById(R.id.email_text);
        mEmailText.setText(user.getEmail());

        List<Transaction> allTransactions = TransactionStorage.getInstance().getTransactions();
        double totalSpending = 0;

        for (Transaction t : allTransactions) {
            totalSpending += t.getTransactionAmount();
        }

        DecimalFormat df = new DecimalFormat("#.00");

        TextView mTotalSpendingText = (TextView) findViewById(R.id.total_spending_text);
        mTotalSpendingText.setText("$"+df.format(totalSpending));

        TextView mRewardsText = (TextView) findViewById(R.id.rewards_text);
        mRewardsText.setText("$"+df.format(totalSpending * Constants.REWARD_PERCENTAGE_PERCENT));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

