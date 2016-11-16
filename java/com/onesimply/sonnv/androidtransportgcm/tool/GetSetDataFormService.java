package com.onesimply.sonnv.androidtransportgcm.tool;


import com.onesimply.sonnv.androidtransportgcm.entities.Image;
import com.onesimply.sonnv.androidtransportgcm.entities.UserNote;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.entities.user;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.onesimply.sonnv.androidtransportgcm.ServerTask.NAMESPACE;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.SERVER_URL;

/**
 * Created by N on 23/02/2016.
 */
public class GetSetDataFormService {

    //- GCM Service
    public boolean UpdateEmailInGCM(String regId, String email){
        String METHOD_NAME = "updateEmailToRegID";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("regId", regId);
        requst.addProperty("email", email);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean status = Boolean.valueOf(soapPrimitive.toString());
            if (status == false){
                return true;
            }else {
                return  false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  false;
    }
    public ArrayList<String> getListRegIdByEmail(String email){
        ArrayList<String> arrayList = new ArrayList<String>();
        try {

            final String METHOD_NAME = "getListRegIDsbyEmail";
            String SOAP_ACTION=NAMESPACE+METHOD_NAME;
            SoapObject request=new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("isDeleted","0");
            request.addProperty("email", email);
            SoapSerializationEnvelope envelope=
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE androidHttpTransport=
                    new HttpTransportSE(SERVER_URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            SoapObject soapArray=(SoapObject) envelope.getResponse();
            arrayList.clear();
            for(int i=0; i<soapArray.getPropertyCount(); i++)
            {
                SoapObject soapItem =(SoapObject) soapArray.getProperty(i);
                String title=soapItem.getProperty("RegId").toString();
                arrayList.add(title);
            }
            // adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public ArrayList<String> getRe(){
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            final String METHOD_NAME = "getListRegIDs";
            String SOAP_ACTION=NAMESPACE+METHOD_NAME;
//service description
//khai báo đối tượng SoapOBject
            SoapObject request=new SoapObject(NAMESPACE, METHOD_NAME);
//thiết lập version
            request.addProperty("isDeleted","0");
            SoapSerializationEnvelope envelope=
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
//thiết lập output
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
//tạo đối tượng HttpTransportSE
            HttpTransportSE androidHttpTransport=
                    new HttpTransportSE(SERVER_URL);
//tiến hành triệu gọi Service
            androidHttpTransport.call(SOAP_ACTION, envelope);

            SoapObject soapArray=(SoapObject) envelope.getResponse();
            arrayList.clear();
            for(int i=0; i<soapArray.getPropertyCount(); i++)
            {
                SoapObject soapItem =(SoapObject) soapArray.getProperty(i);
                String title=soapItem.getProperty("RegId").toString();

                arrayList.add(title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    //-- User login
    public boolean checkLogin(String email, String pwd){
        String METHOD_NAME = "checkLogin";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        requst.addProperty("password", pwd);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean status = Boolean.valueOf(soapPrimitive.toString());
            if (!status){
                return true;
            }else {
                return  false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  false;
    }
    public boolean insertUserLogin(user u){
        String METHOD_NAME = "insertUserLogin";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("emailLogin", u.getEmailLogin().toString());
        requst.addProperty("password", u.getPassword().toString());
        requst.addProperty("fullname" , u.getFullName().toString());
        requst.addProperty("address", u.getAddress().toString());
        requst.addProperty("phone", u.getPhone().toString());
        requst.addProperty("role", u.getRole());
        requst.addProperty("status", u.getStatus().toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean UpdateUserLogin(user u){
        String METHOD_NAME = "updateUserLogin";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", u.getEmailLogin().toString());
        requst.addProperty("fullName" , u.getFullName().toString());
        requst.addProperty("address", u.getAddress().toString());
        requst.addProperty("phone", u.getPhone().toString());
        requst.addProperty("role", u.getRole());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<user> getListUserByEmail(String emailLogin){
        List<user> list = new ArrayList<user>();
        String METHOD_NAME = "getUserLoginByEmailLogin";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("emailLogin", emailLogin);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            list.clear();
            for (int i=0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                user u = new user();
                u.setId(Integer.parseInt(soapItem.getProperty("id").toString()));
                u.setEmailLogin(soapItem.getProperty("Email").toString());
                u.setPassword(soapItem.getProperty("Password").toString());
                u.setFullName(soapItem.getProperty("FullName").toString());
                u.setAddress(soapItem.getProperty("Address").toString());
                u.setPhone(soapItem.getProperty("Phone").toString());
                u.setRole(Integer.parseInt(soapItem.getProperty("Role").toString()));
                u.setStatus(soapItem.getProperty("Status").toString());
                list.add(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public List<user> getListUser(int status){
        List<user> list = new ArrayList<user>();
        String METHOD_NAME = "getListUserLogin";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("status", status);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            list.clear();
            for (int i=0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                user u = new user();
                u.setId(Integer.parseInt(soapItem.getProperty("id").toString()));
                u.setEmailLogin(soapItem.getProperty("Email").toString());
                u.setPassword(soapItem.getProperty("Password").toString());
                u.setFullName(soapItem.getProperty("FullName").toString());
                u.setAddress(soapItem.getProperty("Address").toString());
                u.setPhone(soapItem.getProperty("Phone").toString());
                u.setRate(Integer.parseInt(soapItem.getProperty("Rate").toString()));
                u.setRole(Integer.parseInt(soapItem.getProperty("Role").toString()));
                u.setStatus(soapItem.getProperty("Status").toString());
                list.add(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  list;
    }
    //--- Product
    public List<product> getMyProduct(String emailLogin, int stateId){
        List<product> list = new ArrayList<product>();
        String METHOD_NAME = "getListMyProduct";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("emailLogin", emailLogin);
        requst.addProperty("stateId", stateId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            list.clear();
            for (int i=0; i< soapArray.getPropertyCount(); i++){
                /*SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                product p = new product();
                p.setId(Integer.parseInt(soapItem.getProperty("id").toString()));
                p.setEmailCarrier(soapItem.getProperty("EmailCarrier").toString());
                p.setName(soapItem.getProperty("ProductName").toString());
                p.setCreateDate(soapItem.getProperty("CreateDate").toString());
                p.setCarrier(soapItem.getProperty("Carrier").toString());
                list.add(p);*/
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                product p = new product();
                p.setId(Integer.parseInt(soapItem.getProperty("id").toString()));
                p.setEmailLogin(soapItem.getProperty("EmailLogin").toString());
                p.setEmailCarrier(soapItem.getProperty("EmailCarrier").toString());
                p.setName(soapItem.getProperty("ProductName").toString());
                p.setMyAddress(soapItem.getProperty("Address").toString());
                p.setAddressRec(soapItem.getProperty("AddressRec").toString());
                p.setCreateDate(soapItem.getProperty("CreateDate").toString());
                p.setCreateTime(soapItem.getProperty("CreateTime").toString());
                p.setCarrier(soapItem.getProperty("Carrier").toString());
                p.setStateId(Integer.parseInt(soapItem.getProperty("StateID").toString()));
                list.add(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public List<product> getProductAll(int stateId){
        List<product> list = new ArrayList<product>();
        String METHOD_NAME = "getListProduct";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("stateId", stateId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            list.clear();
            for (int i=0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                product p = new product();
                p.setId(Integer.parseInt(soapItem.getProperty("id").toString()));
                p.setEmailLogin(soapItem.getProperty("EmailLogin").toString());
                p.setEmailCarrier(soapItem.getProperty("EmailCarrier").toString());
                p.setName(soapItem.getProperty("ProductName").toString());
                p.setMyAddress(soapItem.getProperty("Address").toString());
                p.setAddressRec(soapItem.getProperty("AddressRec").toString());
                p.setCreateDate(soapItem.getProperty("CreateDate").toString());
                p.setCreateTime(soapItem.getProperty("CreateTime").toString());
                p.setCarrier(soapItem.getProperty("Carrier").toString());
                list.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public int insertProduct(product p){
        DateTimePicker dateTimePicker = new DateTimePicker();
        String METHOD_NAME = "InsertMyPostProduct";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("emailLogin", p.getEmailLogin().toString());
        requst.addProperty("emailCarrier", p.getEmailCarrier().toString());
        requst.addProperty("name", p.getName().toString());
        requst.addProperty("myAddress" , p.getMyAddress().toString());
        requst.addProperty("addressRec", p.getAddressRec().toString());
        requst.addProperty("deliveryDate",dateTimePicker.formDateDateToStringEN(p.getDeliveryDate()));
        requst.addProperty("deliveryTime", p.getDeliveryTime().toString());
        requst.addProperty("carrier", p.getCarrier().toString());
        requst.addProperty("size", p.getSize().toString());
        requst.addProperty("fee", p.getFee());
        requst.addProperty("payer", p.getPayer());
        requst.addProperty("securityDeposits", p.getSecurityDeposits());
        requst.addProperty("distance", p.getDistance());
        requst.addProperty("image", p.getImage());
        requst.addProperty("receiverName", p.getReceiverName());
        requst.addProperty("receiverPhone", p.getReceiverPhone());
        requst.addProperty("receiverEmail", p.getReceiverEmail());
        requst.addProperty("catgId", p.getCatgId());
        requst.addProperty("stateId", p.getStateId());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            int item = Integer.parseInt(soapPrimitive.toString());
            if(item>0){
                return item;
            }else {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getStateIdProduct(int id ){
        String METHOD_NAME = "getStateIDProduct";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            int item = Integer.parseInt(soapPrimitive.toString());
            if(item>0){
                return item;
            }else {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int checkCarrier(int id, String carrier){
        int n = -1;
        String METHOD_NAME = "checkCarrier";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        requst.addProperty("carrier", carrier);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            int item = Integer.parseInt(soapPrimitive.toString());
            if(item == 1){
                n =  1;
            }else {
                n = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return n;
    }
    public List<product> getProductByID(int id){
        List<product> list = new ArrayList<product>();
        String METHOD_NAME = "getListProductbyID";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            list.clear();
            for (int i=0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                product p = new product();
                p.setId(Integer.parseInt(soapItem.getProperty("id").toString()));
                p.setEmailLogin(soapItem.getProperty("EmailLogin").toString());
                p.setEmailCarrier(soapItem.getProperty("EmailCarrier").toString());
                p.setName(soapItem.getProperty("ProductName").toString());
                p.setMyAddress(soapItem.getProperty("Address").toString());
                p.setAddressRec(soapItem.getProperty("AddressRec").toString());
                p.setCreateDate(soapItem.getProperty("CreateDate").toString());
                p.setCreateTime(soapItem.getProperty("CreateTime").toString());
                p.setCarrier(soapItem.getProperty("Carrier").toString());
                p.setStateId(Integer.parseInt(soapItem.getProperty("StateID").toString()));
                list.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public List<product> getProductReceived(String  emailCarrier){
        List<product> list = new ArrayList<product>();
        String METHOD_NAME = "getListProdouctReceived";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("emailCarrier", emailCarrier);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            list.clear();
            for (int i=0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                product p = new product();
                p.setId(Integer.parseInt(soapItem.getProperty("id").toString()));
                p.setEmailLogin(soapItem.getProperty("EmailLogin").toString());
                p.setEmailCarrier(soapItem.getProperty("EmailCarrier").toString());
                p.setName(soapItem.getProperty("ProductName").toString());
                p.setMyAddress(soapItem.getProperty("Address").toString());
                p.setAddressRec(soapItem.getProperty("AddressRec").toString());
                p.setCreateDate(soapItem.getProperty("CreateDate").toString());
                p.setCreateTime(soapItem.getProperty("CreateTime").toString());
                p.setCarrier(soapItem.getProperty("Carrier").toString());
                list.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public boolean updateCarrier(int id,String emailCarrier, String carrier){
        String METHOD_NAME = "updateCarrierToProduct";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        requst.addProperty("emailCarrier", emailCarrier);
        requst.addProperty("carrier", carrier);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateStateIdProduct(int id, int stateId){
        String METHOD_NAME = "setStateProductInfo";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        requst.addProperty("stateId", stateId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteProduct(int id){
        String METHOD_NAME = "deleteProduct";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---- PRODUCT CATEGORIES

    public ArrayList<String> GetProductCategory(){
        ArrayList<String> list = new ArrayList<String>();
        String METHOD_NAME = "GetProductCategories";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            for (int i = 0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                String name = soapItem.getProperty("catgName").toString();
                list.add(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }
    // ---- IMAGE
    public boolean InserImage(String id, String image){
        String METHOD_NAME = "InsertImage";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        /*PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("imageArray");
        propertyInfo.setType(MarshalBase64.BYTE_ARRAY_CLASS);
        propertyInfo.setValue(image);*/
        requst.addProperty("imageArray", image);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        new MarshalBase64().register(envelope);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Image> GetListImage(String id){
        ArrayList<Image> list = new ArrayList<Image>();
        String METHOD_NAME = "GetImageUseID";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("id", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            for (int i = 0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                String ids = soapItem.getProperty("id").toString();
                byte[] imageArray = (byte[]) soapItem.getProperty("imageArray");
                Image image = new Image();
                image.setId(ids);
                image.setIamge(imageArray);
                list.add(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean deleteImage(String id){
        String METHOD_NAME = "deleteImage";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("id", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }

    // USER RATE
    public boolean insertUserRate(String email, int rate,int productId){
        String METHOD_NAME = "insertUserRate";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        requst.addProperty("rate", rate);
        requst.addProperty("prodcutId", productId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getRate(String email){
        String METHOD_NAME = "getRate";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            String item = String.valueOf(soapPrimitive.toString());
            int n = Integer.parseInt(item);
            return n;
        } catch (Exception ex) {
            return 0;
        }
    }
    public int getRateCount(String email){
        String METHOD_NAME = "getRateCount";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            int n = Integer.parseInt(soapPrimitive.toString());
            return n;
        } catch (Exception ex) {
            return 0;
        }
    }
    public boolean checkRated(String email, int productId){
        String METHOD_NAME = "checkRated";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        requst.addProperty("productId", productId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }

    // USER NOTE
    public boolean insertUserNote(String email, int productId){
        String METHOD_NAME = "insertUserNote";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        requst.addProperty("productId", productId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<UserNote> getListUserNote(String email){
        List<UserNote> list = new ArrayList<UserNote>();
        String METHOD_NAME = "getUserNote";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapArray = (SoapObject) envelope.getResponse();
            list.clear();
            for (int i=0; i< soapArray.getPropertyCount(); i++){
                SoapObject soapItem = (SoapObject) soapArray.getProperty(i);
                UserNote userNote = new UserNote();
                userNote.setEmail(soapItem.getProperty("Email").toString());
                userNote.setProductId(Integer.parseInt(soapItem.getProperty("ProductId").toString()));
                list.add(userNote);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public boolean deleteUserNote(String email, int productId){
        String METHOD_NAME = "deleteUserNote";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SoapObject requst = new SoapObject(NAMESPACE, METHOD_NAME);
        requst.addProperty("email", email);
        requst.addProperty("productId",  productId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(requst);
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(envelope);
        HttpTransportSE httpTransportSE = new HttpTransportSE(SERVER_URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
            Boolean item = Boolean.valueOf(soapPrimitive.toString());
            if(item == true){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return false;
    }
}
