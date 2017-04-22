package net.androidbootcamp.sheltersrcapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oldi1_000 on 4/22/2017.
 */




import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

    /**
     * Created by Hai on 2/19/2017.
     */
    public class MapRequest  extends StringRequest{

        //sends signal to url from web database to access through php - Hai
        private static final String MAP_REQUEST_URL = "https://haitphan.000webhostapp.com/map.php";
        private Map<String, String> params;

        //Only need username and password to check on database - Hai
        //LoginRequest is Constructor to ask for username and password to send to php - Hai
        //This constructor is used by the LoginActivity.java - Hai
        public MapRequest(String providerId, Response.Listener<String> listener ) {

            super(Request.Method.POST, MAP_REQUEST_URL, listener, null);
            params = new HashMap<>();

            //put data into HashMap-Hai
            //This data is passed to the Login.php
            params.put("providerId", providerId);

        }

        //When request is executed volley needs to access data-Hai
        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }


