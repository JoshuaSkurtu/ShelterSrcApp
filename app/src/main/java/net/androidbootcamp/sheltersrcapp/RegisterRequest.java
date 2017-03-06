package net.androidbootcamp.sheltersrcapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp197 on 2/19/2017.
 */

public class RegisterRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "https://haitphan.000webhostapp.com/Register.php";
    private Map<String, String> params;

    //makes Object RegisterRequest that holds all the parameters of the EditText
    public RegisterRequest(String name, String lname, String username, int age, String password, String password2, String email, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        //put("nameThatWillbeSentToPHPFileOnServer", variable name in RegisterRequest)
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("password2", password2);
        params.put("age", age + "");
        params.put("lname", lname);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
