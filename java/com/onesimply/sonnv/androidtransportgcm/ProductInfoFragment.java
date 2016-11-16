package com.onesimply.sonnv.androidtransportgcm;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.asyncs.SPproductAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.CustomListPostProductAllAdapter;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductInfoFragment extends Fragment {

    public ArrayList<product> dsProduct;
    public CustomListPostProductAllAdapter adapter;

    public ProductInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_info, container, false);
        final ListView lvProduct = (ListView) view.findViewById(R.id.listView_Product_All);
        GetSetDataFormService sv = new GetSetDataFormService();
        dsProduct = new ArrayList<product>();
        adapter = new CustomListPostProductAllAdapter(getActivity(), dsProduct);
        lvProduct.setAdapter(adapter);
        getProduct();
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lvProduct.getItemAtPosition(position);
                product pd = (product) o;
                //Toast.makeText(getActivity(), "Selected :" + " " + pd.getEmailLogin() + pd.getId(), Toast.LENGTH_LONG).show();
                openDialog(pd.getEmailLogin(), pd.getEmailCarrier(), pd.getName(), pd.getId(), pd.getMyAddress(), pd.getAddressRec(), pd.getCreateDate(), pd.getCreateTime(), pd.getCarrier());
            }
        });

        return view;

    }
    private void getProduct(){
        SPproductAsyncTask SPproductAsyncTask = new SPproductAsyncTask(this);
        SPproductAsyncTask.execute();
    }
    private void openDialog(String emailLogin, String emailCarier,String productName, final int id, String myAddress, String AddressRec, String createDate, String createTime, final String carrier){
        GetSetDataFormService sv = new GetSetDataFormService();
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(getString(R.string.detail));

        dialog.setContentView(R.layout.dialog_product_detail);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        TextView textView_name = (TextView)dialog.findViewById(R.id.textView_productName_dialog);
        TextView textView_myAddress = (TextView) dialog.findViewById(R.id.textView_myAddress_dialog);
        TextView textView_addressRec = (TextView) dialog.findViewById(R.id.textView_addressRec_dialog);
        TextView textView_creatDate = (TextView) dialog.findViewById(R.id.textView_createDate_dialog);
        TextView textView_carrier = (TextView) dialog.findViewById(R.id.textView_carrier_dialog);
        Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel_dialog);
        final Button button_xn = (Button) dialog.findViewById(R.id.button_xn_dialog);
        textView_name.setText(productName);
        textView_myAddress.setText(myAddress);
        textView_addressRec.setText(AddressRec);
        textView_creatDate.setText(createDate + "( " + createTime + " )");

        if(emailLogin.equals(MainActivity.emaiLogin)){
            button_xn.setEnabled(false);
            button_xn.setText("Sửa");
        }
        else {
            button_xn.setEnabled(true);
            button_xn.setText("Nhận");
        }
        if (carrier.equals("isNull")){
            textView_carrier.setText("Chưa có", TextView.BufferType.NORMAL);
        }else {
            if(emailCarier.equals(MainActivity.emaiLogin)){
                textView_carrier.setText(carrier);
                button_xn.setEnabled(false);
                button_xn.setText("Đã nhận");
            }else {
                textView_carrier.setText(carrier);
                button_xn.setEnabled(false);
                button_xn.setText("Đã có người nhận");
            }
        }
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSetDataFormService sv = new GetSetDataFormService();
                int n = sv.checkCarrier(id, carrier);
                if (n == 1){
                   // Toast.makeText(getActivity(), "Co th nhan van chuyen cai nay", Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Bạn muốn nhận vận chuyển món hàng này?")
                            .setCancelable(false)
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    GetSetDataFormService sv = new GetSetDataFormService();
                                    List<user> listUser = new ArrayList<user>();
                                    listUser = sv.getListUserByEmail(MainActivity.emaiLogin);
                                    boolean rs = sv.updateCarrier(id, MainActivity.emaiLogin ,listUser.get(0).getFullName().toString());
                                    if (rs){
                                        Toast.makeText(getActivity(), "Nhan thanh cong.", Toast.LENGTH_LONG).show();
                                        button_xn.setEnabled(false);
                                        button_xn.setText("Đã nhận");
                                        dialog.cancel();
                                    }

                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                }else {
                    button_xn.setEnabled(false);
                    button_xn.setText("Đã có người nhận");
                }
            }
        });
        dialog.show();
    }
}
