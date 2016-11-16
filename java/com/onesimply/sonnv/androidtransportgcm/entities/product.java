package com.onesimply.sonnv.androidtransportgcm.entities;

import java.util.Date;

/**
 * Created by N on 23/02/2016.
 */
public class product {
    private int id;
    private String emailLogin;
    private String emailCarrier;
    private String name;
    private String myAddress;
    private String addressRec;
    private String createDate;
    private String createTime;
    private Date deliveryDate;
    private String deliveryTime;
    private String carrier;
    private String size;
    private float fee;
    private int payer;
    private float securityDeposits;
    private float distance;
    private String image;
    private String receiverName;
    private String receiverPhone;
    private String receiverEmail;
    private int catgId;
    private int stateId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getEmailCarrier() {
        return emailCarrier;
    }

    public void setEmailCarrier(String emailCarrier) {
        this.emailCarrier = emailCarrier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public String getAddressRec() {
        return addressRec;
    }

    public void setAddressRec(String addressRec) {
        this.addressRec = addressRec;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getPayer() {
        return payer;
    }

    public void setPayer(int payer) {
        this.payer = payer;
    }

    public float getSecurityDeposits() {
        return securityDeposits;
    }

    public void setSecurityDeposits(float securityDeposits) {
        this.securityDeposits = securityDeposits;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public int getCatgId() {
        return catgId;
    }

    public void setCatgId(int catgId) {
        this.catgId = catgId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", emailLogin='" + emailLogin + '\'' +
                ", emailCarrier='" + emailCarrier + '\'' +
                ", name='" + name + '\'' +
                ", myAddress='" + myAddress + '\'' +
                ", addressRec='" + addressRec + '\'' +
                ", createDate='" + createDate + '\'' +
                ", createTime='" + createTime + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", carrier='" + carrier + '\'' +
                ", size='" + size + '\'' +
                ", fee=" + fee +
                ", payer=" + payer +
                ", securityDeposits=" + securityDeposits +
                ", distance=" + distance +
                ", image='" + image + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", catgId=" + catgId +
                ", stateId=" + stateId +
                '}';
    }

    public product() {

    }

    public product(int id, String emailLogin, String emailCarrier, String name, String myAddress, String addressRec, String createDate, String createTime, Date deliveryDate, String deliveryTime, String carrier, String size, float fee, int payer, float securityDeposits, float distance, String image, String receiverName, String receiverPhone, String receiverEmail, int catgId, int stateId) {
        this.id = id;
        this.emailLogin = emailLogin;
        this.emailCarrier = emailCarrier;
        this.name = name;
        this.myAddress = myAddress;
        this.addressRec = addressRec;
        this.createDate = createDate;
        this.createTime = createTime;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.carrier = carrier;
        this.size = size;
        this.fee = fee;
        this.payer = payer;
        this.securityDeposits = securityDeposits;
        this.distance = distance;
        this.image = image;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverEmail = receiverEmail;
        this.catgId = catgId;
        this.stateId = stateId;
    }
}
