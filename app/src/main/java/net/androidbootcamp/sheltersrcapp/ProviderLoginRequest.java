package net.androidbootcamp.sheltersrcapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp197 on 3/10/2017.
 */

/*

Still need to write notepad to write to database for this to work;
 */

public class ProviderLoginRequest extends StringRequest{
    //Creates url string that connects to web domain providerLogin.php-Hai
    private static final String PROVLOGIN_REQUEST_URL = "https://haitphan.000webhostapp.com/providerLogin.php";
    private Map<String, String> params;

    public ProviderLoginRequest(String code,
                                Response.Listener<String> listener) {
        //Execute request by Method.POST by sending to the Register.php to send data over-Hai
        super(Request.Method.POST, PROVLOGIN_REQUEST_URL, listener, null);
        //Creates object hashmap for container-Hai
        params = new HashMap<>();

        //put data into HashMap-Hai
        //Data is used to be passed to providerLogin.php
        params.put("code", code);
    }
    //When request is executed volley needs to access data-Hai

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
