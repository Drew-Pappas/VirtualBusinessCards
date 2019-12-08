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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(String userOrg) {
        this.userOrg = userOrg;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public QRSnapshot getUserToBeAddedSnapshot() {
        return userToBeAddedSnapshot;
    }

    public void setUserToBeAddedSnapshot(QRSnapshot userToBeAddedSnapshot) {
        this.userToBeAddedSnapshot = userToBeAddedSnapshot;
    }
}
