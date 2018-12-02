package com.example.db.imagegallery;

/**
 * Created by user on 9/30/2018.
 */

public class Person {
    private byte[] img;
    public Person(byte[] img){
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
