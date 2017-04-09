package net.androidbootcamp.sheltersrcapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Hai on 2/19/2017.
 */

public class UserAreaActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //instantiates the editText from xml to be filled with information from json response - Hai
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etAge = (EditText)findViewById(R.id.etAge);
        final TextView welcomeMessage = (TextView)findViewById(R.id.tvWelcomeMsg);

        //gets intent information from previous page that pulled from response of php - Hai
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        String message = name + ", welcome to your user area";
        welcomeMessage.setText(message);
        //sets the editText with the filled data from intent - Hai
        etUsername.setText(username);
        etAge.setText(age + "");
    }

}
