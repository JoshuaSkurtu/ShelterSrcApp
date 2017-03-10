package net.androidbootcamp.sheltersrcapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hai on 2/19/2017.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //icon - Hai
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //variables for Login - Hai
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        final Button bLogin = (Button)findViewById(R.id.bLogin);
        final TextView registerLink = (TextView)findViewById(R.id.tvRegisterHere);

        //Button for the Register Here - Hai
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent to open activity
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //Button for Login - Hai
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username= etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            //gathering data from json -Hai
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                //gets the json reponse that we got in the login.php -Hai
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");

                                /*
                                Kept code, modifying so that login takes the user to map screen.
                                // make intent to go to UserAreaActivity
                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);

                                //intent is connected to the getIntent() method-Hai
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("age", age);*/

                                Intent intent = new Intent(LoginActivity.this, map.class);


                                //actually use method to change screens - Hai
                                LoginActivity.this.startActivity(intent);

                            } else{
                                //Creates Error message if they get registration wrong - Hai
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

                //Makes a request to the server database for username, password information - Hai
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
