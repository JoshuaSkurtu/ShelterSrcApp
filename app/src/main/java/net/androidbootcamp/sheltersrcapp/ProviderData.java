package net.androidbootcamp.sheltersrcapp;

/**
 * Created by Joshua Skurtu on 4/21/2017.
 */

public class ProviderData {


    public String providerName;
    public String providerAddress;
    public int housingAvail;
    public int housingBool;
    public int foodBool;
    public int clothingBool;

    public ProviderData(String a, String b, int c, int d, int e, int f){ //constructor -Josh
        providerName = a;
        providerAddress = b;
        housingAvail= c;
        housingBool = d;
        foodBool = e;
        clothingBool = f;

    }
}
