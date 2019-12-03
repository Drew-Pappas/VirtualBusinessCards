package com.example.virtualbusinesscards;

public class QRSnapshot {
    public String userID;
    public String userName;
    public String userEmail;
    public String userPhone;
    public String userRole;
    public String userOrg;
    public String userLocation;
    public String userBio;
    public long timestamp;
    public QRSnapshot userToBeAddedSnapshot;


    public QRSnapshot(){
    }

    //set entered user to the variables declared
    public QRSnapshot(String userID, String userName, String userEmail,
                String userPhone, String userRole, String userOrg,
                String userLocation, String userBio) {


        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userRole = userRole;
        this.userOrg = userOrg;
        this.userLocation = userLocation;
        this.userBio = userBio;
        this.timestamp = System.nanoTime();
        this.userToBeAddedSnapshot = new QRSnapshot();

    }
}
