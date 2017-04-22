package net.androidbootcamp.sheltersrcapp;

import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.FloatingActionButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.androidbootcamp.sheltersrcapp.R.id.bGuestLogin;
 /* Created by Joshua Skurtu on 2/19/2017.
         */
public class map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    public static List<Marker> markerList = new ArrayList<>();
     private static final String LOGIN_REQUEST_URL = "https://haitphan.000webhostapp.com/Login.php";







//function needed that populates marker list below based on locations we have in the database in a whileloop maybe
     public String callServer()
     {
         final String providerId = "1";
         //creates response listener to send to LoginRequest - Hai
         Response.Listener<String> responseListener = new Response.Listener<String>(){
             @Override
             public void onResponse(String response) {
                 try {
                     //gathering data from response Listener to json -Hai
                     JSONObject jsonResponse = new JSONObject(response);
                     //Sent from php file
                     boolean success = jsonResponse.getBoolean("success");

                     if (success){
                         //gets the json reponse that we got in the login.php -Hai
                         //This is from the mysqli_stmt_fetch($statement) from login.php
                         String providerName = jsonResponse.getString("providerName");


                     } else{
                         //Creates Error message if they get registration wrong - Hai
                         AlertDialog.Builder builder = new AlertDialog.Builder(map.this);
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
         //Makes a LoginRequest to the server database for username, password information - Hai
         //Uses the LoginRequest Constructor from LoginRequest.java - Hai
         MapRequest loginRequest = new MapRequest( providerId, responseListener);
         RequestQueue queue = Volley.newRequestQueue(map.this);
         queue.add(loginRequest);





     }




    public void markerCreation(LatLng yourPosition, String fTitle) //use this method to add a marker to the list - starts hidden - josh
        {
            //create JSON method  to provide info from database to the parameters below :"name, address, etc" They are then used to create the providerData class object - Josh
            //We might also just put it in the ProviderData object and have it called in the constructor.
            //At this method we are supposed to already have the address, which is how we have the latlng,
            //It should be called from the oncreate, as well, so that it only happens once. Not sure what the best method would be. - Josh


             String providerName = fTitle;
             String providerAddress="";
             int housingAvail=1;
             boolean housingBool =true;
             boolean foodBool= true;
             boolean clothingBool=true;


            Marker fMarker = mMap.addMarker(new MarkerOptions().position(yourPosition).visible(true).title(fTitle));
            fMarker.setTag(new ProviderData(providerName,providerAddress,housingAvail,housingBool,foodBool,clothingBool)); //Associates the maker with an object for the purpose of storing more data on the location - JOsh
            markerList.add(0, fMarker);
        }
     public void onMapUpdate(){ //creates markers when updating the map -Josh
         mMap.clear(); //clear


         LatLng testLatLng =  new LatLng(38,-90);
         markerCreation(testLatLng,"Test Title Field for New Marker");
         LatLng new1 =  new LatLng(38.01,-90.01);
         markerCreation(new1, "ABC Shelter");
         LatLng new2 =  new LatLng(38.7760,-90.5287);
         markerCreation(new2, "Food Pantry Alpha");
         LatLng new3 =  new LatLng(37.8,-89.33);
         markerCreation(new3, "three");
         LatLng new4 =  new LatLng(38.9,-91);
         markerCreation(new4, "four");
         LatLng new5 =  new LatLng(32,-90);
         markerCreation(new5, "five");

     }

    public void showMarkers(LatLng location, float distance) //This then reveals any markers in the range you choose nearby the location - josh
    {

        for(Marker marker : markerList){
                    LatLng mLocation = marker.getPosition();
                if(distance(location.latitude,location.longitude, mLocation.latitude,mLocation.longitude)<=distance) //we find distance between two pts to reveal only markers near usS
                marker.setVisible(true);

            else{
                marker.setVisible(false);
            }

        }

    }
     @Override
     public boolean onMarkerClick(Marker marker) { //This is called when a user clicks a marker

         ProviderData data= (ProviderData) marker.getTag();//retrieves data stored in ProviderData object - Now we can use this data to show more info when they click a location
         String providerName = data.providerName;
         String providerAddress = data.providerAddress;
         int housingAvail = data.housingAvail;
         boolean housingBool = data.housingBool;
         boolean foodBool = data.foodBool;
         boolean clothingBool = data.clothingBool;


         return false;
     }

    private double distance(double latitude, double longitude, double latitude1, double longitude1) {
       //calculates distance between two latlon points - Josh
        final int R = 6371;

        Double latDistance = Math.toRadians(latitude-latitude1);
        Double lonDistance = Math.toRadians(longitude-longitude1);
        Double a = Math.sin(latDistance/2)*Math.sin(latDistance/2)
                +Math.cos(Math.toRadians(latitude))*Math.cos(Math.toRadians(latitude1))
                * Math.sin(lonDistance/2) * Math.sin(lonDistance/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c * 1000;

        //double height = 0; //not needed - josh

        distance = Math.pow(distance,2); //redundant, because we sqrt, but keeping other part if you were to calculate elevation (+Math.pow(height,2);)

        return Math.sqrt(distance);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //adding drawer button - Josh
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This section for making navigation menu buttons and switches work - Josh
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navLogin:
                        Intent loginIntent = new Intent(map.this, LoginActivity.class);

                        map.this.startActivity(loginIntent);


                }




                return false;
            }
        });


    }

    //more for button - Josh
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }



    //makes back buton close drawer - Josh
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
//add ability to search on map - josh
    public void onSearch(View view) throws IOException {
        mMap.clear(); // Clears all previous markers on map
        EditText location_new = (EditText)findViewById(R.id.TFaddress);
        String location = location_new.getText().toString();
        List<Address> addressList = null;
        if(location != null || !location.equals("") )
        {
            Geocoder geocoder = new Geocoder(this);

           try {
              addressList = geocoder.getFromLocationName(location, 1);
               //if(geocoder.isPresent()){}
           }
           catch(IOException e){
               e.printStackTrace();
           }
           Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Search"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            onMapUpdate();
            showMarkers(latLng,200000);

        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMarkerClickListener(this); //Listens for marker click
    }


}
