package net.androidbootcamp.sheltersrcapp;

/**
 * Created by Joshua Skurtu on 4/21/2017.
 */

public class ProviderData {


    public String providerName;
    public String providerAddress;
    public int housingAvail;
    public boolean housingBool;
    public boolean foodBool;
    public boolean clothingBool;



    public ProviderData(String a, String b, int c, boolean d, boolean e, boolean f){ //constructor -Josh
        providerName = a;
        providerAddress = b;
        housingAvail= c;
        housingBool = d;
        foodBool = e;
        clothingBool = f;





    }
}
