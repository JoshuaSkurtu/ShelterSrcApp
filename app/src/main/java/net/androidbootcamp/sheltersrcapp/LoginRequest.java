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
    private static final String LOGIN_REQUEST_URL = "https://haitphan.000webhostapp.com/Login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        //Execute request by Method.POST by sending to the Register.php to send data over
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        //put data into HashMap
        params.put("username", username);
        params.put("password", password);
    }

    //When request is executed volley needs to access data

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
