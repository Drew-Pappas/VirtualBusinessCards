package com.example.virtualbusinesscards;

public class User {

    //Declare variables for new user object
    public String userID;
    public String userName;
    public String userEmail;
    public String userPhone;
    public String userRole;
    public String userOrg;
    public String userLocation;
    public String userBio;
    public Settings userShareSettings;
    public String userPhotoURI;

    public void setUserPhotoURI(String userPhotoURI) {
        this.userPhotoURI = userPhotoURI;
    }
//TODO Add user photo

    public User(){
    }

    //set entered user to the variables declared
    public User(String userID, String userName, String userEmail,
                String userPhone, String userRole, String userOrg,
                String userLocation, String userBio) {

        //TODO Add user photo

        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userRole = userRole;
        this.userOrg = userOrg;
        this.userLocation = userLocation;
        this.userBio = userBio;
        this.userShareSettings = new Settings();
        this.userPhotoURI = "";

    }
}
