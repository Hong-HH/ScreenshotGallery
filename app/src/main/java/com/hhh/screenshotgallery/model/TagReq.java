package com.hhh.screenshotgallery.model;

public class TagReq {
    private String photo_url;

    public TagReq(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
