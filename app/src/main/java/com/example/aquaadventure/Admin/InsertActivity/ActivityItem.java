package com.example.aquaadventure.Admin.InsertActivity;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class CardItem {
    private String imageUrl;
    private String title;
    private String description;
    private String location;
    private double price;
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

    public double getPrice() {
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
    public CardItem() {
    }

    public CardItem(String title, String imageUrl, String description, String location, double price, String date, String startTime, String endTime, String duration) {
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
