package net.androidbootcamp.sheltersrcapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ProviderAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_area);

        //logo- Hai
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Grab the textView message and Editext from xml - Hai
        //to edit with response from ProviderAuthenticationActivity.java using intent from intent.putExtra - Hai
        final EditText etHousingNumber = (EditText)findViewById(R.id.etHousingNumber);
//        final EditText etHousing = (EditText)findViewById(R.id.etHousing);
//        final EditText etFood = (EditText)findViewById(R.id.etFood);
//        final EditText etClothing = (EditText)findViewById(R.id.etClothing);
        final TextView tvWelcome = (TextView)findViewById(R.id.tvWelcome);
        final TextView tvAddress = (TextView)findViewById(R.id.tvAddress);

        //gets intent information from previous page that pulled from response of php - Hai
        Intent intent = getIntent();

        //Instantiates the variables from intent.putExtra to use
        String providerName = intent.getStringExtra("provider_name");
        String providerAddress = intent.getStringExtra("provider_address");
        int housingNumber = intent.getIntExtra("housing_number", -1);
        String housing = intent.getStringExtra("housing");
        String food = intent.getStringExtra("food");
        String clothing = intent.getStringExtra("clothing");

        String welcome = "Welcome " + providerName;
        tvWelcome.setText(welcome);
        tvAddress.setText(providerAddress);
        etHousingNumber.setText(housingNumber + "");
//        etHousing.setText(housing);
//        etFood.setText(food);
//        etClothing.setText(clothing);
    }
}
