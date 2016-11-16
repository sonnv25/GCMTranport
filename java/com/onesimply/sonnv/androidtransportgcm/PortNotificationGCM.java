package com.onesimply.sonnv.androidtransportgcm;

/**
 * Created by N on 18/02/2016.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import java.util.ArrayList;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.*;
public class PortNotificationGCM {
    public ArrayList<String> getRegID() {
        ArrayList<String>arr=new ArrayList<String>();
        try{
//hàm cần truy suất
            //final String METHOD_NAME="getListTinTuc";
            final String METHOD_NAME = "getListRegIDs";
            final String SOAP_ACTION=NAMESPACE+METHOD_NAME;
//service description
//khai báo đối tượng SoapOBject
            SoapObject request=new SoapObject(NAMESPACE, METHOD_NAME);
//thiết lập version

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
            for(int i=0; i<soapArray.getPropertyCount(); i++)
            {
//(SoapObject) soapArray.getProperty(i) get item at position i
                SoapObject soapItem =(SoapObject) soapArray.getProperty(i);
//soapItem.getProperty("CateId") get value of CateId property
//phải mapp đúng tên cột:
                String id=soapItem.getProperty("RegNo").toString();
                String title=soapItem.getProperty("RegId").toString();
                String tin=id+" - "+title;
                arr.add(tin);
            }
        }
        catch(Exception e)    {
//Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            Log.i("LOI_DOC_TIN", e.toString());
        }
        return arr;

    }
}
