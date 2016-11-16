package com.onesimply.sonnv.androidtransportgcm.entities;

/**
 * Created by N on 29/03/2016.
 */
public class UserNote {
    private int id;
    private String email;
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserNote{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", productId=" + productId +
                '}';
    }

    public UserNote() {
    }

    public UserNote(int id, String email, int productId) {
        this.id = id;
        this.email = email;
        this.productId = productId;
    }
}
