package com.example.aquaadventure.Instructor;

public class BookingModel {
    private int bookingId;
    private String activityName;
    private String description;
    private String location;
    private String price;
    private String date;
    private String startTime;
    private String endTime;
    private String duration;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userEmail;
    private String bookingDateTime;
    private int status;

    // Empty constructor for Firebase
    public BookingModel() {}

    public BookingModel(int bookingId, String activityName, String description, String location, String price, String date, String startTime, String endTime, String duration, String userName, String userPhone, String userAddress, String userEmail, String bookingDateTime) {
        this.bookingId = bookingId;
        this.activityName = activityName;
        this.description = description;
        this.location = location;
        this.price = price;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.bookingDateTime = bookingDateTime;
    }

    // Constructor with fields
    public BookingModel(String activityName, String userName, String userPhone, String activityLocation, String activityDate, String startTime, String endTime) {
        this.activityName = activityName;
        this.userName = userName;
        this.userPhone = userPhone;
        this.location = activityLocation;
        this.date = activityDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters for Firebase to map the data
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public String getActivityLocation() { return location; }
    public void setActivityLocation(String activityLocation) { this.location = activityLocation; }

    public String getActivityDate() { return date; }
    public void setActivityDate(String activityDate) { this.date = activityDate; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    // Add a getter and setter for the status attribute
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }
    public String toString() {
        return activityName + " had booked by " + userName + " at " + bookingDateTime + " with phone number " + userPhone + " and address " + userAddress + "." ; // Customize the string as needed
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}



