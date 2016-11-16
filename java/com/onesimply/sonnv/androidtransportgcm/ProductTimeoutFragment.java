package com.onesimply.sonnv.androidtransportgcm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.onesimply.sonnv.androidtransportgcm.asyncs.PTproductTimeOutAsync;
import com.onesimply.sonnv.androidtransportgcm.entities.ArrayClass;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.CustomListAdapterSelectUserPost;
import com.onesimply.sonnv.androidtransportgcm.tool.CustomListPostProductAllAdapter;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.io.IOException;
import java.util.ArrayList;




public class ProductTimeoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ListView listView;
    public ArrayList<product> dsProduct;
    public CustomListPostProductAllAdapter adapter;
    public int productId;
    public AsyncTask<Void, Void, ArrayList<String>> asyncArr;

    public AsyncTask<Void, Void, ArrayList<user>> asyncTaskUser;
    public AsyncTask<Void, Void, Boolean> asyncTaskUpdateStateId;
    public GetSetDataFormService getSetDataFormService;
    public ArrayList<user> dsUser;
    public CustomListAdapterSelectUserPost adapterSelectUserPost;

    public String emailCarricer;
    public String productName;

    private int index =0;

    public ProgressDialog mProgressDialog;
    // TODO: Rename and change types and number of parameters
    public ProductTimeoutFragment() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_timeout, container, false);
        getSetDataFormService = new GetSetDataFormService();
        dsProduct = new ArrayList<product>();
        listView = (ListView) view.findViewById(R.id.listView_product_time_out);
        adapter = new CustomListPostProductAllAdapter(getActivity(), dsProduct);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                product p = (product) o;
                productId = p.getId();
                productName = p.getName().toString();
                showPopupMenu(p.getEmailCarrier().toString(), p.getId(), view);
            }
        });
        try {
            getProduct();
        }catch (Exception ex){

        }
        mProgressDialog = new ProgressDialog(getActivity());
        
        return view;
    }
    private void showPopupMenu(final String email, final int id, View view){
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_repost, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_repost) {
                    ShowDialogSelectTypePost();
                } else if (item.getItemId() == R.id.item_show_detail) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("id", id + "");
                    startActivity(intent);
                }
                return true;
            }

        });
        popupMenu.show();
    }
    private void ShowDialogSelectTypePost(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Chọn kiểu");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_select_type_post);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final ListView listView = (ListView) dialog.findViewById(R.id.listView_select_type_post);
        final ListView listView_show_user = (ListView) dialog.findViewById(R.id.listView_show_user);
        final Button button_next = (Button) dialog.findViewById(R.id.button_next_post);
        button_next.setText(getString(R.string.send));
        Button button_back = (Button) dialog.findViewById(R.id.button_back_post);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, ArrayClass.type_post));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean rs = false;
                if (position == 1) {
                    rs = true;
                }else if(position ==2){
                   index = 3;
                }
                else if(position ==0) {
                    index =1;
                }
                listView_show_user.setVisibility(rs ? View.VISIBLE : View.GONE);
                button_next.setVisibility(rs ? View.GONE : View.VISIBLE);
            }
        });
        dsUser = new ArrayList<user>();
        adapterSelectUserPost = new CustomListAdapterSelectUserPost(getActivity(), dsUser);
        listView_show_user.setAdapter(adapterSelectUserPost);
        listView_show_user.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListUser();
        listView_show_user.setVisibility(View.GONE);
        listView_show_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), dsUser.get(position).toString(), Toast.LENGTH_SHORT).show();
                emailCarricer = dsUser.get(position).getEmailLogin().toString();
                showDialogConfirmSend(dsUser.get(position).getFullName().toString());
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 1) {
                    asyncArr = new AsyncTask<Void, Void, ArrayList<String>>() {
                        @Override
                        protected ArrayList<String> doInBackground(Void... params) {
                            ArrayList<String> list = new ArrayList<String>();
                            list = getSetDataFormService.getRe();
                            return list;
                        }
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            Toast.makeText(getActivity(), "Đăng gửi ...", Toast.LENGTH_SHORT);
                        }

                        @Override
                        protected void onPostExecute(ArrayList<String> arrayList) {
                            super.onPostExecute(arrayList);
                            Toast.makeText(getActivity(), "Gửi thanhg công !", Toast.LENGTH_SHORT);
                            if (!arrayList.isEmpty()) {
                                updateState(dialog);
                                sendMessagingGCM(getString(R.string.gcm_new_post) + "@" + MainActivity.emaiLogin +"@"+productId, arrayList);
                            }
                            asyncArr = null;
                        }
                    };
                    asyncArr.execute();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    private void getProduct() {
        PTproductTimeOutAsync timeOutAsync = new PTproductTimeOutAsync(this);
        timeOutAsync.execute();
    }
    private void updateState(final Dialog dialog){
        asyncTaskUpdateStateId = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return getSetDataFormService.updateStateIdProduct(productId, 0);
            }

            @Override
            protected void onPreExecute() {
                mProgressDialog.setMessage(getString(R.string.waiting));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(mProgressDialog.isShowing()){
                    mProgressDialog.dismiss();
                }
                if(aBoolean){
                    dialog.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.complete), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "gửi lại lỗi, chưa thay đổi được.", Toast.LENGTH_SHORT).show();
                }
                asyncTaskUpdateStateId = null;
            }
        };
        asyncTaskUpdateStateId.execute();
    }
    private void getListUser(){
        asyncTaskUser = new AsyncTask<Void, Void, ArrayList<user>>() {
            @Override
            protected ArrayList<user> doInBackground(Void... params) {
                ArrayList<user> arrayList = (ArrayList<user>) getSetDataFormService.getListUser(1);
                return arrayList;
            }

            @Override
            protected void onPreExecute() {
                Toast.makeText(getActivity(),getString(R.string.waiting), Toast.LENGTH_SHORT).show();
            }
            @Override
            protected void onPostExecute(ArrayList<user> users) {
                super.onPostExecute(users);
                dsUser.clear();
                dsUser.addAll(users);
                adapter.notifyDataSetChanged();
                asyncTaskUser = null;
            }
        };
        asyncTaskUser.execute();
    }
    private void showDialogConfirmSend(String fullName){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Gửi thông báo")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Gửi yêu cầu cho " + fullName)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!emailCarricer.isEmpty()) {
                            asyncArr = new AsyncTask<Void, Void, ArrayList<String>>() {
                                @Override
                                protected ArrayList<String> doInBackground(Void... params) {
                                    ArrayList<String> arrayList = getSetDataFormService.getListRegIdByEmail(emailCarricer);
                                    return arrayList;
                                }
                                @Override
                                protected void onPreExecute() {
                                    mProgressDialog.setMessage(getString(R.string.waiting));
                                    mProgressDialog.setCancelable(false);
                                    mProgressDialog.show();
                                }
                                @Override
                                protected void onPostExecute(ArrayList<String> arrayList) {
                                    super.onPostExecute(arrayList);
                                    if (mProgressDialog.isShowing()) {
                                        mProgressDialog.dismiss();
                                    }
                                    if (!arrayList.isEmpty()) {
                                        String message = getString(R.string.gcm_request_tranport) + "@Time@" + 300 + "@" + MainActivity.emaiLogin + "@" + productName+"@"+productId;
                                        Toast.makeText(getActivity(), productId+"", Toast.LENGTH_SHORT).show();
                                        sendMessagingGCM(message, arrayList);
                                    } else {
                                        Toast.makeText(getActivity(), "User chưa cài ứng dụng", Toast.LENGTH_SHORT).show();
                                    }
                                    asyncArr = null;
                                }
                            };
                            asyncArr.execute();
                        }
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
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

