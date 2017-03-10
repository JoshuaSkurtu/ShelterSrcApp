package net.androidbootcamp.sheltersrcapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hai on 2/19/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Create Logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Create Java Variables that pull from XML ids that is stored in Java R file - Hai
        final EditText etEmail = (EditText)findViewById(R.id.etEmail);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etLName = (EditText) findViewById(R.id.etLName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.etPassword2);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        //OnClick Listener for Register Button - Hai
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //final int age = Integer.parseInt(etAge.getText().toString());
                final int age = Integer.parseInt("0" + etAge.getText().toString());
                final String email = etEmail.getText().toString();
                final String name = etName.getText().toString();
                final String lname = etLName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String password2 = etPassword2.getText().toString();

                //Callback interface for delivering parsed responses - Hai
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override

                    //Called when a response is received. - Hai
                    public void onResponse(String response) {
                        try {
                            /*//gets JSONObject from php file
                            //A modifiable set of name/value mappings.
                            //When the requested type is a String,
                            // other non-null values will be coerced using valueOf(Object).
                            // Although null cannot be coerced,
                            // the sentinel value NULL is coerced to the string "null" -Hai*/
                            JSONObject jsonResponse = new JSONObject(response);
                            //gets if connection is successful
                            boolean success = jsonResponse.getBoolean("success");

                            //id success==true from json php file then proceed
                            if(success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed, Please try again.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //this sends all the data - Hai
                RegisterRequest registerRequest = new RegisterRequest(name, lname, username, age, password, password2, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
