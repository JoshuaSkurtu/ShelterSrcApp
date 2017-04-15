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

import com.android.volley.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.design.widget.Snackbar;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.androidbootcamp.sheltersrcapp.R.id.bGuestLogin;

public class map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    public static List<Marker> markerList = new ArrayList<>();







//function needed that populates marker list below based on locations we have in the database in a whileloop maybe

    public void markerCreation(LatLng yourPosition, String fTitle) //use this method to add a marker to the list - starts hidden - josh
        {
            Marker fMarker = mMap.addMarker(new MarkerOptions().position(yourPosition).visible(true).title(fTitle));
            markerList.add(0, fMarker);
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

    public void onMapUpdate(){
        mMap.clear(); //clear
        //private static final String LOGIN_REQUEST_URL = "https://haitphan.000webhostapp.com/Login.php";

    //Put Json here

       //create variables containing these:
        //string providerName=
        //string providerAddress=
        //int housingAvail=
        //bool housingBool=
        //bool foodBool =
        //bool clothingBool =

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}
