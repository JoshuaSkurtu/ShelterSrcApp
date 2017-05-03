package net.androidbootcamp.sheltersrcapp;

import android.Manifest;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

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

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/* Created by Joshua Skurtu on 2/19/2017.
        */
public class map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
     private static String providerName1 = "unchanged";
     private static String providerAddress1 = "unchanged";
     private static int housingNumber1 = 0;
     private static boolean housing1 = false;
     private static boolean food1= false;
     private static boolean clothing1 = false;

    private GoogleMap mMap;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    public static List<Marker> markerList = new ArrayList<>();
     private static final String LOGIN_REQUEST_URL = "https://haitphan.000webhostapp.com/Login.php";








     public void callServer(int providerId)
     {

         //creates response listener
         Response.Listener<String> responseListener = new Response.Listener<String>(){
             @Override
             public void onResponse(String response) {
                 try {
                     //gathering data from response Listener to json -Hai
                     JSONObject jsonResponse = new JSONObject(response);
                     //Sent from php file
                     boolean success = jsonResponse.getBoolean("success");

                     if (success){

                         providerName1 = jsonResponse.getString("provider_name");

                         providerAddress1 = jsonResponse.getString("provider_address");
                         housingNumber1 = Integer.parseInt(jsonResponse.getString("housing_number"));
                         housing1 = Boolean.parseBoolean(jsonResponse.getString("housing"));
                         food1 = Boolean.parseBoolean(jsonResponse.getString("food"));
                         clothing1 = Boolean.parseBoolean(jsonResponse.getString("clothing"));


                     } else{
                         //Creates Error message
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

         MapRequest loginRequest = new MapRequest( providerId, responseListener);
         RequestQueue queue = Volley.newRequestQueue(map.this);
         queue.add(loginRequest);




         //return providerName1;


     }




   // public void markerCreation(LatLng yourPosition, String fTitle) //use this method to add a marker to the list - starts hidden - josh
     public void markerCreation(ProviderData provider) {



         String location = provider.providerAddress;
         List<Address> addressList = null;
         LatLng newGeo = null;
         if (location != null || !location.equals("")) {
             Geocoder geocoder = new Geocoder(this);

             try {
                 addressList = geocoder.getFromLocationName(location, 1);
                 //if(geocoder.isPresent()){}
             } catch (IOException e) {
                 e.printStackTrace();
             }
             Address address = addressList.get(0);
             newGeo = new LatLng(address.getLatitude(), address.getLongitude());


         }

         Marker fMarker = mMap.addMarker(new MarkerOptions().position(newGeo).visible(true).title(provider.providerName).snippet(provider.providerAddress));
         fMarker.setTag(provider); //Associates the marker with an object for the purpose of storing  data on the location - Josh
         //markerList.add(0, fMarker);
         markerList.add( fMarker);
     }
     public void onMapUpdate(){ //creates markers when updating the map -Josh
         mMap.clear(); //clear
         int index = 1;
         while(index<6)
         {
             callServer(index);
             ProviderData provider = new ProviderData(providerName1,providerAddress1,housingNumber1, housing1,food1,clothing1);
             markerCreation(provider);

             index +=1;
         }


//         LatLng testLatLng =  new LatLng(38,-90);
//         markerCreation(testLatLng,"Test Title Field for New Marker");
//         LatLng new1 =  new LatLng(38.01,-90.01);
//         markerCreation(new1, "ABC Shelter");
//         LatLng new2 =  new LatLng(38.7760,-90.5287);
//         markerCreation(new2, "Food Pantry Alpha");
//         LatLng new3 =  new LatLng(37.8,-89.33);
//         markerCreation(new3, "three");
//         LatLng new4 =  new LatLng(38.9,-91);
//         markerCreation(new4, "four");
//         LatLng new5 =  new LatLng(32,-90);
//         markerCreation(new5, "five");

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

         //adding fragment when marker is clicked
         android.app.FragmentManager fragmentManager = getFragmentManager();
         android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         ProviderFragment providerFragment = new ProviderFragment();
         fragmentTransaction.add(R.id.provider_frag_container, providerFragment);
         fragmentTransaction.commit();

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

        //callServer(2);






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
            showMarkers(latLng,200000000);

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
