package com.onesimply.sonnv.androidtransportgcm.entities;

/**
 * Created by N on 27/02/2016.
 */
public class user {
    private int id;
    private String emailLogin;
    private String password;
    private String fullName;
    private String address;

    private String phone;
    private int rate;
    private int role;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", emailLogin='" + emailLogin + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", rate=" + rate +
                ", role=" + role +
                ", status='" + status + '\'' +
                '}';
    }

    public user(int id, String emailLogin, String password, String fullName, String address, String phone, int rate, int role, String status) {
        this.id = id;
        this.emailLogin = emailLogin;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.rate = rate;
        this.role = role;
        this.status = status;
    }

    public user() {

    }

}
