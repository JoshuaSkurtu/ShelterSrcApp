package net.androidbootcamp.sheltersrcapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hai on 2/19/2017.
 */

public class LoginRequest extends StringRequest {
    //sends signal to url from web database to access through php - Hai
    private static final String LOGIN_REQUEST_URL = "https://haitphan.000webhostapp.com/Login.php";
    private Map<String, String> params;

    //Only need username and password to check on database - Hai
    //LoginRequest is Constructor to ask for username and password to send to php - Hai
    //This constructor is used by the LoginActivity.java - Hai
    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        //Execute request by Method.POST by sending to the Login.php to send data over-Hai
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        //put data into HashMap-Hai
        //This data is passed to the Login.php
        params.put("username", username);
        params.put("password", password);
    }

    //When request is executed volley needs to access data-Hai
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
