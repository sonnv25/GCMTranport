package com.onesimply.sonnv.androidtransportgcm.entities;

import java.util.Arrays;

/**
 * Created by N on 18/03/2016.
 */
public class Image {
    private String id;
    private byte[] image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getIamge() {
        return image;
    }

    public void setIamge(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    public Image(byte[] iamge, String id) {

        this.image = iamge;
        this.id = id;
    }

    public Image() {
    }

}
