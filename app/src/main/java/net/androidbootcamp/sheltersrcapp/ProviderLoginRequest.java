package net.androidbootcamp.sheltersrcapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by hp197 on 3/10/2017.
 */

public class ProviderLoginRequest extends StringRequest{


    public ProviderLoginRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
