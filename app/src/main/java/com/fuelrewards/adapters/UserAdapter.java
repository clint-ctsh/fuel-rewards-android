package com.fuelrewards.adapters;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.fuelrewards.common.Constants;
import com.fuelrewards.exceptions.NetworkException;
import com.fuelrewards.models.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserAdapter {

    private static UserAdapter userAdapter;
    private RequestQueue queue;

    protected UserAdapter(Activity activity) {
        queue = Volley.newRequestQueue(activity);
    }

    public static void initialize(Activity activity) {
        userAdapter = new UserAdapter(activity);
    }

    public static UserAdapter getInstance() {
        return userAdapter;
    }

    public User login(String email, String password) throws NetworkException {

        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email", email);
            jsonRequest.put("password", password);

            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Constants.LOGIN_URL, jsonRequest, future, future)
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

            String cardNumber = response.getString("cardNumber");
            String authToken = response.getString("token");
            String firstName = response.getString("firstName");
            String lastName = response.getString("lastName");

            return new User(email, password, firstName, lastName, cardNumber, authToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NetworkException("Error during login", e);
        }
    }

    public User signup(String email, String firstName,
                              String lastName, String password) throws NetworkException {

        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email", email);
            jsonRequest.put("firstName", firstName);
            jsonRequest.put("lastName", lastName);
            jsonRequest.put("password", password);

            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Constants.SIGNUP_URL, jsonRequest, future, future)
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

            String cardNumber = response.getString("cardNumber");
            String authToken = response.getString("token");

            return new User(email, password, firstName, lastName, cardNumber, authToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NetworkException("Error during signup", e);
        }

    }
}
