package com.fuelrewards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fuelrewards.adapters.TransactionAdapter;
import com.fuelrewards.adapters.UserAdapter;
import com.fuelrewards.exceptions.NetworkException;
import com.fuelrewards.models.Transaction;
import com.fuelrewards.models.User;
import com.fuelrewards.storage.TransactionStorage;
import com.fuelrewards.storage.UserStorage;

public class SubmitTransactionActivity extends AppCompatActivity {

    private SubmitTransactionActivity.SubmitTransactionTask mSubmitTask = null;

    // UI references.
    private EditText mTransactionNameView;
    private EditText mTransactionAmountView;
    private View mProgressView;
    private View mSubmitTransactionView;
    private TextView mErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_transaction);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mErrorText = (TextView) findViewById(R.id.error_textview);

        // Set up the submit transaction form.
        mTransactionNameView = (EditText) findViewById(R.id.transaction_name);
        mTransactionAmountView = (EditText) findViewById(R.id.transaction_amount);

        Button mSubmitTransactionButton = (Button) findViewById(R.id.submit_transaction_button);
        mSubmitTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptTransactionSubmit();
            }
        });

        mSubmitTransactionView = findViewById(R.id.transaction_submit_form);
        mProgressView = findViewById(R.id.submit_progress);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(SubmitTransactionActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptTransactionSubmit() {
        if (mSubmitTask != null) {
            return;
        }

        // Reset errors.
        mTransactionNameView.setError(null);
        mTransactionAmountView.setError(null);

        // Store values at the time of the transacion attempt.
        String transactionName = mTransactionNameView.getText().toString();
        Double transactionAmount = Double.parseDouble(mTransactionAmountView.getText().toString().isEmpty()
                ? "0" : mTransactionAmountView.getText().toString());

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mSubmitTask = new SubmitTransactionActivity.SubmitTransactionTask(transactionName, transactionAmount);
            mSubmitTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSubmitTransactionView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSubmitTransactionView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSubmitTransactionView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSubmitTransactionView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SubmitTransactionTask extends AsyncTask<Void, Void, Boolean> {

        private final String mTransactionName;
        private final Double mTransactionAmount;

        SubmitTransactionTask(String transactionName, Double transactionAmount) {
            mTransactionName = transactionName;
            mTransactionAmount = transactionAmount;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Transaction newTransaction = null;

            try {
                newTransaction = TransactionAdapter.getInstance().submitTransaction(mTransactionName, mTransactionAmount);
            } catch (NetworkException e) {
                return false;
            }

            if (newTransaction != null) {
                TransactionStorage.getInstance().addTransaction(newTransaction); // store transaction
            }

            return newTransaction != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSubmitTask = null;
            showProgress(false);

            if (success) {
                finish(); // return to main page
            } else {
                mErrorText.setError("An error occurred, please try again later");
            }
        }

        @Override
        protected void onCancelled() {
            mSubmitTask = null;
            showProgress(false);
        }
    }
}
