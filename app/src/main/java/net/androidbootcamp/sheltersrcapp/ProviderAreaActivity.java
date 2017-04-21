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
        final TextView tvHousingNumber = (TextView) findViewById(R.id.tvHousingNumber);
        final TextView tvHousing = (TextView) findViewById(R.id.tvHousing);
        final TextView tvFood = (TextView) findViewById(R.id.tvFood);
        final TextView tvClothing = (TextView) findViewById(R.id.tvClothing);
        final TextView tvWelcome = (TextView)findViewById(R.id.tvWelcome);
        final TextView tvAddress = (TextView)findViewById(R.id.tvAddress);
        final EditText etUpdateHousing = (EditText)findViewById(R.id.etUpdateHousingNum) ;

        //gets intent information from previous page that pulled from response of php - Hai
        Intent intent = getIntent();

        //Instantiates the variables from intent.putExtra to use
        String providerName = intent.getStringExtra("provider_name");
        String providerAddress = intent.getStringExtra("provider_address");
        String updateHousingNum = intent.getStringExtra("housing_number");
        int housingNumber = intent.getIntExtra("housing_number", -1);
        int housing = intent.getIntExtra("housing",-1);
        int food = intent.getIntExtra("food", -1);
        int clothing = intent.getIntExtra("clothing", -1);

        String welcome = "Welcome " + providerName;
        tvWelcome.setText(welcome);
        tvAddress.setText(providerAddress);
        tvHousingNumber.setText("This is the number of beds: " + housingNumber + "");
        etUpdateHousing.setText(housingNumber + "");

        if(housing==1){
            tvHousing.setText("Housing available.");
        }else{
            tvHousing.setText("No housing at this location.");
        }
        if(food==1){
            tvFood.setText("Food service available.");
        }else{
            tvFood.setText("No food at this location.");
        }
        if(clothing==1){
            tvClothing.setText("Clothing available.");
        }else{
            tvClothing.setText("No clothing at this location.");
        }



    }
}
