package com.hhh.screenshotgallery.model;

public class PhotoReq {
    private String title;
    private String date;
    private String photo_time;

    public PhotoReq(String title, String date, String photo_time) {
        this.title = title;
        this.date = date;
        this.photo_time = photo_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto_time() {
        return photo_time;
    }

    public void setPhoto_time(String photo_time) {
        this.photo_time = photo_time;
    }
}
