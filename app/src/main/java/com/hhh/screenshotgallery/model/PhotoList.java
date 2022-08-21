package com.hhh.screenshotgallery.model;

import java.util.List;

public class PhotoList {

    private int error;
    private int count;
    private List<Photo> list;

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

    public List<Photo> getList() {
        return list;
    }

    public void setList(List<Photo> list) {
        this.list = list;
    }
}
