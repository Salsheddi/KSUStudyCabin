package com.example.ksustudycabin;

public class StudyRoom {
    private int id;
    private String roomName;
    private int capacity;
    private String location;
    private String isAvailable;
    private String openTime;
    private String closeTime;
    private String amenities;
    private String photoPath;

    // Constructor
    public StudyRoom(int id, String roomName, int capacity, String location, String isAvailable, String openTime, String closeTime, String amenities, String photoPath) {
        this.id = id;
        this.roomName = roomName;
        this.capacity = capacity;
        this.location = location;
        this.isAvailable = isAvailable;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.amenities = amenities;
        this.photoPath = photoPath;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String isAvailable() {
        return isAvailable;
    }

    public void setAvailable(String available) {
        isAvailable = available;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}

