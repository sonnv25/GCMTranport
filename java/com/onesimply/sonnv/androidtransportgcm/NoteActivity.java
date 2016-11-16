package com.onesimply.sonnv.androidtransportgcm;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import static com.onesimply.sonnv.androidtransportgcm.ServerTask.NAMESPACE;
import static com.onesimply.sonnv.androidtransportgcm.ServerTask.SERVER_URL;

public class NoteActivity extends AppCompatActivity {

    public ArrayAdapter<String> adapter;
    public ArrayList<String> dsReg;
    private ListView listReg;
    private EditText txtContent;
    String API_Key = "AIzaSyDSutvmKtoG5nmsv_VTfz7TLZyWsfRqX8s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        listReg = (ListView)findViewById(R.id.listRegistationID);
        txtContent = (EditText)findViewById(R.id.txtContent);
        dsReg = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, dsReg);
        listReg.setAdapter(adapter);
        getRe();
        Toast.makeText(getApplication(), dsReg.size() +"", Toast.LENGTH_LONG).show();

        Button btnsub = (Button) findViewById(R.id.btnClik);
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessagingGCM();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getRe(){
        try {
//hàm cần truy suất
            // String METHOD_NAME="getListTinTuc";
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
            dsReg.clear();
            for(int i=0; i<soapArray.getPropertyCount(); i++)
            {
//(SoapObject) soapArray.getProperty(i) get item at position i
                SoapObject soapItem =(SoapObject) soapArray.getProperty(i);
//soapItem.getProperty("CateId") get value of CateId property
//phải mapp đúng tên cột:
                String id=soapItem.getProperty("RegNo").toString();
                String title=soapItem.getProperty("RegId").toString();
                String tin=id+" - "+title;
                dsReg.add(title);
            }
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
    public void sendMessagingGCM(){
        try {
             Sender sender = new Sender(API_Key);
                Message message = new Message.Builder()
                        .collapseKey("message")
                        .timeToLive(30)
                        .delayWhileIdle(true)
                        .addData("message", txtContent.getText().toString()) //you can get this message on client side app
                        .build();
                    MulticastResult multicastResult = sender.send(message, dsReg, 0);

                    Toast.makeText(this, multicastResult.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
