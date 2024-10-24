package com.example.aquaadventure.Admin.InsertActivity;

public class ActivityItem {
    private String imageUrl;
    private String title;
    private String description;
    private String location;
    private String price;
    private String date;
    private String startTime;
    private String endTime;
    private String duration;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDuration() {
        return duration;
    }

    // Constructors
    public ActivityItem() {
    }

    public ActivityItem(String title, String imageUrl, String description, String location, String price, String date, String startTime, String endTime, String duration) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

}
