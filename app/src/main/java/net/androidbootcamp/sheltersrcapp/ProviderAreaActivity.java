package net.androidbootcamp.sheltersrcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProviderAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_area);

        //logo- Hai
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Grab the textView message and Editext from xml to edit with response from ProviderAuthenticationActivity.java using intent from intent.putExtra - Hai

    }
}
