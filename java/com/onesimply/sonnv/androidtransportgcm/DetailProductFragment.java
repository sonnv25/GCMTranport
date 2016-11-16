package com.onesimply.sonnv.androidtransportgcm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.onesimply.sonnv.androidtransportgcm.asyncs.UpdateCarricerAsync;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;
import com.onesimply.sonnv.androidtransportgcm.tool.SetStateProduct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DetailProductFragment extends Fragment {

    public DetailProductFragment() {
        // Required empty public constructor
    }
    private List<product> listProduct;
    private TextView textView_name;
    private TextView textView_myAddress;
    private TextView textView_addressRec;
    private TextView textView_datetime;
    private TextView textView_carrier;
    private TextView textView_poster;

    private RadioButton radioButton_posted;
    private RadioButton radioButton_received;
    private RadioButton radioButton_complete;

    private TextView textView_a;
    private TextView textView_b;

    private SetStateProduct setStateProduct;

    public Button button_deny;
    public Button button_accept;

    private String FEEDBACK;

    private AsyncTask<Void, Void, Integer> asyncTaskCheck;
    private GetSetDataFormService getSetDataFormService;
    ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        mProgressDialog = new ProgressDialog(getActivity());

        textView_name = (TextView) view.findViewById(R.id.textView_productName_detail);
        textView_myAddress = (TextView) view.findViewById(R.id.textView_myAddress_detail);
        textView_addressRec = (TextView) view.findViewById(R.id.textView_addressRec_detail);
        textView_datetime = (TextView) view.findViewById(R.id.textView_dateTime_detail);
        textView_carrier = (TextView) view.findViewById(R.id.textView_carrier_detail);
        textView_poster = (TextView) view.findViewById(R.id.textView_poster_detail);

        radioButton_posted = (RadioButton) view.findViewById(R.id.radioButton_a);
        radioButton_received = (RadioButton) view.findViewById(R.id.radioButton_b);
        radioButton_complete = (RadioButton) view.findViewById(R.id.radioButton_c);

        textView_a = (TextView) view.findViewById(R.id.textView_a);
        textView_b = (TextView) view.findViewById(R.id.textView_b);

        button_deny = (Button) view.findViewById(R.id.button_deny_detail);
        button_accept = (Button) view.findViewById(R.id.button_accept_detail);

        setStateProduct = new SetStateProduct(getActivity(), radioButton_posted, radioButton_received, radioButton_complete, textView_a, textView_b);

        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_buttom_confirm_detail);
        linearLayout.setVisibility(DetailActivity.note ? View.VISIBLE : View.GONE);
        Intent intent = getActivity().getIntent();
        getSetDataFormService = new GetSetDataFormService();
        try {
            listProduct = new ArrayList<product>();
            listProduct = getSetDataFormService.getProductByID(DetailActivity.id);
            textView_name.setText(listProduct.get(0).getName().toString());
            textView_myAddress.setText(listProduct.get(0).getMyAddress().toString());
            textView_addressRec.setText(listProduct.get(0).getAddressRec().toString());
            textView_datetime.setText(listProduct.get(0).getCreateDate().toString());
            textView_poster.setText(listProduct.get(0).getEmailLogin());
            String carrier = listProduct.get(0).getCarrier().toString();
            int stateId = listProduct.get(0).getStateId();
            setStateProduct.setData(carrier, stateId);
            if(!carrier.equals("isNull")){
                textView_carrier.setText(carrier);
            }else {
                textView_carrier.setText("Chưa có");
            }
        }catch (Exception ex){
            Toast.makeText(getActivity(), "Lỗi hiển thị thông tin sản phẩm. ", Toast.LENGTH_LONG).show();
        }
        button_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MainActivity.emaiLogin.equals("")) {
                    FEEDBACK  ="FEEDBACK_NO@"+MainActivity.emaiLogin;
                    sendFeedBack(DetailActivity.email, FEEDBACK);
                    getActivity().finish();
                }else {
                    Toast.makeText(getActivity(), "emailLogin is null!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MainActivity.emaiLogin.equals("")) {
                    FEEDBACK = "FEEDBACK_YES@" + MainActivity.emaiLogin;
                    sendFeedBack(DetailActivity.email, FEEDBACK);
                    updateCarricer(DetailActivity.id);
                    linearLayout.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getActivity(), "emailLogin is null!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return  view;
    }
    private void sendFeedBack(final String email, final String title){
        final GetSetDataFormService getSetDataFormService = new GetSetDataFormService();
        AsyncTask<Void, Void, ArrayList<String>> asyncTask = new AsyncTask<Void, Void, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Void... params) {
                return getSetDataFormService.getListRegIdByEmail(email);
            }
            @Override
            protected void onPreExecute() {

            }
            @Override
            protected void onPostExecute(ArrayList<String> arrayList) {
                super.onPostExecute(arrayList);
                if(!arrayList.isEmpty()) {
                    if(isAdded()){
                        sendMessagingGCM(title, arrayList);
                        Toast.makeText(getActivity(), getString(R.string.complete), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        asyncTask.execute();
    }
    private void updateCarricer(final int produciId){
        asyncTaskCheck = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return getSetDataFormService.checkCarrier(produciId, "isNull");
            }
            @Override
            protected void onPreExecute() {
                mProgressDialog.setMessage(getString(R.string.waiting));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if(mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
                if(integer ==1){
                    update(produciId);
                }else {
                    Toast.makeText( getActivity(),getString(R.string.note_has_been_received),Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    // Update đã có người nhận
    private void update(int productid){
        UpdateCarricerAsync updateCarricerAsync = new UpdateCarricerAsync(getActivity(), productid, MainActivity.emaiLogin, 2);
        updateCarricerAsync.execute();
    }
    public void sendMessagingGCM(String title, ArrayList<String> arrayList){
        try {
            Sender sender = new Sender(getString(R.string.API_KEY));
            Message message = new Message.Builder()
                    .collapseKey("message")
                    .timeToLive(30)
                    .delayWhileIdle(true)
                    .addData("message", title)
                    .build();
            MulticastResult multicastResult = sender.send(message, arrayList, 0);
            //   Toast.makeText(this, multicastResult.getTotal() + "", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
