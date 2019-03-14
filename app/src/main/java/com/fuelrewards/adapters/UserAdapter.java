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

    public static User fetchProfile(String email, String password) throws NetworkException {

        return null;
    }

    public static User updateProfile(User user) throws NetworkException {

        return null;
    }

    public static User login(String email, String password, Activity activity) throws NetworkException {

        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email", email);
            jsonRequest.put("password", password);

            RequestQueue queue = Volley.newRequestQueue(activity);

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

    public static User signup(String email, String firstName,
                              String lastName, String password, Activity activity) throws NetworkException {

        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email", email);
            jsonRequest.put("firstName", firstName);
            jsonRequest.put("lastName", lastName);
            jsonRequest.put("password", password);

            RequestQueue queue = Volley.newRequestQueue(activity);

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
