package com.hhh.screenshotgallery.model;

import java.util.List;

public class PhotoTagList {
    private int error;
    private int count;
    private List<PhotoTag> result;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PhotoTag> getResult() {
        return result;
    }

    public void setResult(List<PhotoTag> result) {
        this.result = result;
    }
}

