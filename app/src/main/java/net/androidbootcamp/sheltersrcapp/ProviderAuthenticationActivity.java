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

public class ProviderAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_authentication);

        //icon - Hai
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Make variable for code drawing from xml-Hai
        final EditText etCodeNum = (EditText)findViewById(R.id.etCodeNum);

        //make variable for button-Hai
        final Button bProviderLogin = (Button)findViewById(R.id.btnProviderLogin);

        //make button on click for login-Hai
        bProviderLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Grab the code from android to confirm match on the database - Hai
                final String code = etCodeNum.getText().toString();

                //Get a response Listener to get information from php-Hai
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            //Make a json object to store the string from php-Hai
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                //gets the json reponse that we got in the providerLogin.php -Hai
                                String provider_name = jsonResponse.getString("provider_name");
                                String provider_address = jsonResponse.getString("provider_address");
                                int housing_number = jsonResponse.getInt("housing_number");
                                int housing = jsonResponse.getInt("housing");
                                int food = jsonResponse.getInt("food");
                                int clothing = jsonResponse.getInt("clothing");

                                //creates intent to move to providerAreaActivity-Hai
                                Intent intent = new Intent(ProviderAuthenticationActivity.this, ProviderAreaActivity.class);

                                //puts data so it can be used on next page - Hai
                                intent.putExtra("provider_name", provider_name);
                                intent.putExtra("provider_address", provider_address);
                                intent.putExtra("housing_number", housing_number);
                                intent.putExtra("housing", housing);
                                intent.putExtra("food", food);
                                intent.putExtra("clothing", clothing);

                                //Moves to next activity page - Hai
                                ProviderAuthenticationActivity.this.startActivity(intent);

                            }else{
                                //Make alert box for error-Hai
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProviderAuthenticationActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }//end if/else-Hai
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }//end try/catch-Hai
                    }//end onResponse()-Hai
                };//end Response.Listener-Hai

                //Makes a request to the server database for code information - Hai
                //Need database to have code info for this to work;
                ProviderLoginRequest providerLoginRequest = new ProviderLoginRequest(code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProviderAuthenticationActivity.this);
                queue.add(providerLoginRequest);

            }//end onClick(View v)-Hai

        });//end new Button onclick()-Hai
    }//ond onCreate()-Hai
}//end ProviderAuthenticationActivity()-Hai
