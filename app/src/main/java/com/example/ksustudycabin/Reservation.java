package com.example.ksustudycabin;

public class Reservation {
    private int id;
    private String studentEmail;
    private int roomId;
    private String startTime;
    private String endTime;
    private String memberEmails;

    // Constructors
    public Reservation() {
    }

    public Reservation(int id, String studentEmail, int roomId, String startTime, String endTime, String memberEmails) {
        this.id = id;
        this.studentEmail = studentEmail;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.memberEmails = memberEmails;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMemberEmails() {
        return memberEmails;
    }

    public void setMemberEmails(String memberEmails) {
        this.memberEmails = memberEmails;
    }

    // Optionally, if you plan to display or use a string representation of the Reservation object:
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", studentEmail='" + studentEmail + '\'' +
                ", roomId=" + roomId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", memberEmails='" + memberEmails + '\'' +
                '}';
    }
}
