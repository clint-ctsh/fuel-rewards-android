package com.fuelrewards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fuelrewards.adapters.TransactionAdapter;
import com.fuelrewards.adapters.UserAdapter;
import com.fuelrewards.storage.UserStorage;

public class MainActivity extends AppCompatActivity {

    Button mSubmitTransactionButton;
    Button mListTransactionsButton;
    Button mLoginButton;
    Button mSignupButton;
    Button mProfileButton;
    Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize adapters
        UserAdapter.initialize(MainActivity.this);
        TransactionAdapter.initialize(MainActivity.this);

        mSubmitTransactionButton = (Button) findViewById(R.id.submit_transaction_button);
        mSubmitTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubmitTransactionActivity.class);
                startActivity(intent);
            }
        });

        mListTransactionsButton = (Button) findViewById(R.id.list_transactions_button);
        mListTransactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListTransactionsActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mSignupButton = (Button) findViewById(R.id.signup_button);
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        mProfileButton = (Button) findViewById(R.id.profile_button);
        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        mLogoutButton = (Button) findViewById(R.id.logout_button);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserStorage.getInstance().logout();
                reloadPage();
            }
        });
    }

    private void reloadPage() {
        if (UserStorage.getInstance().isUserLoggedIn()) {

            // hide login/signup because user already logged in
            mLoginButton.setVisibility(View.GONE);
            mSignupButton.setVisibility(View.GONE);

            // show transaction and profile functionality
            mListTransactionsButton.setVisibility(View.VISIBLE);
            mSubmitTransactionButton.setVisibility(View.VISIBLE);
            mProfileButton.setVisibility(View.VISIBLE);
            mLogoutButton.setVisibility(View.VISIBLE);
        } else {

            // user not logged, so show login/signup
            mLoginButton.setVisibility(View.VISIBLE);
            mSignupButton.setVisibility(View.VISIBLE);

            // if user not logged in, hide transaction and profile functionality
            mListTransactionsButton.setVisibility(View.GONE);
            mSubmitTransactionButton.setVisibility(View.GONE);
            mProfileButton.setVisibility(View.GONE);
            mLogoutButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadPage();
    }
}