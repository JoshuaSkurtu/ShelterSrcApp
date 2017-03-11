package net.androidbootcamp.sheltersrcapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ProviderAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_authentication);

        //Make variable for code drawing from xml-Hai
        final EditText etCodeNum = (EditText)findViewById(R.id.etCodeNum);

        //make variable for button-Hai
        final Button bProviderLogin = (Button)findViewById(R.id.btnProviderLogin);

        //make button on click for login-Hai
        bProviderLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the xml String input-Hai
                final String code = etCodeNum.getText().toString();

                //Get a response Listener to get information from php
                Response.Listener<String> response = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            //Make a json object to store the string from php
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                //gets the json reponse that we got in the login.php -Hai

                            }else{
                                //Make alert box for error
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProviderAuthenticationActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

            }
        });
    }
}
