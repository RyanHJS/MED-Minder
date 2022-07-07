package com.example.medminder.bean;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class myListBean implements Serializable {

    String title;
    boolean isCheck;

    public myListBean(String title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public myListBean() {

    }

    public myListBean(String title) {
        this.title = title;
        this.isCheck = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "myListBean{" +
                "title='" + title + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
