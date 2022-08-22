package com.hhh.screenshotgallery.model;

import java.util.List;

public class TagRes {
    private int error;
    private List<Tag> list;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<Tag> getList() {
        return list;
    }

    public void setList(List<Tag> list) {
        this.list = list;
    }
}
