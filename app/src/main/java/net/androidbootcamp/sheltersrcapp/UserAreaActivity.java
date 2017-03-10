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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        //icon - Hai
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //variables for displaying username, age, and Welcome message - Hai
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etAge = (EditText)findViewById(R.id.etAge);
        final TextView welcomeMessage = (TextView)findViewById(R.id.tvWelcomeMsg);

        //getIntent grabs the data from intent.putExtra from last LoginActivity - Hai
        Intent intent = getIntent();
        //intent.getStringExtra grabs all the names from other LoginActivity
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        String message = name + ", welcome to your user area";
        welcomeMessage.setText(message);
        etUsername.setText(username);
        etAge.setText(age + "");
    }
}
