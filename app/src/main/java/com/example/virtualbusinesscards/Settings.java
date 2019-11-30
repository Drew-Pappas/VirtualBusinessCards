package com.example.virtualbusinesscards;

public class Settings {

    public boolean shareUserEmail;
    public boolean shareUserPhone;
    public boolean shareUserOrg;
    public boolean shareUserLocation;


    public Settings(){
        this.shareUserEmail = true;
        this.shareUserPhone = true;
        this.shareUserOrg = true;
        this.shareUserLocation = true;

    }

    public static String checkShareSetting(boolean setting, String reference) {
        if (setting) {
            return reference;
        } else {
            return "";
        }

    }


}
