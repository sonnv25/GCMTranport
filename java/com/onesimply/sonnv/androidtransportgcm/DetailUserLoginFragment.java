package com.onesimply.sonnv.androidtransportgcm;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.asyncs.RatingUserAsync;
import com.onesimply.sonnv.androidtransportgcm.entities.user;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.List;


public class DetailUserLoginFragment extends Fragment {



    public DetailUserLoginFragment() {
        // Required empty public constructor
    }
    private TextView textView_fullName;
    private TextView textView_email;
    private TextView textView_phone;
    private List<user> listUser;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private Button button_rate;

    public AsyncTask<Void, Void, Boolean> asyncTaskCheckRated;
    public AsyncTask<Void, Void, Integer> asyncTaskRateCount;
    private GetSetDataFormService getSetDataFormService;

    public RatingBar ratingBar;
    public TextView textView_dialog;
    public Button button_send_dialog;

    public RatingBar ratingBar_detail;
    public TextView textView_rate;
    public TextView textView_rate_count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_user_login, container, false);
        textView_fullName = (TextView) view.findViewById(R.id.textView_fullName_detail);
        textView_email = (TextView) view.findViewById(R.id.textView_email_detail);
        textView_phone = (TextView) view.findViewById(R.id.textView_phone_detail);
        imageView = (ImageView) view.findViewById(R.id.imageView_isNull);
        linearLayout = (LinearLayout)view.findViewById(R.id.linearLayoutShowUserInfo);
        getSetDataFormService = new GetSetDataFormService();
        button_rate = (Button) view.findViewById(R.id.button_rate);

        ratingBar_detail = (RatingBar) view.findViewById(R.id.ratingBar_user_detail);
        textView_rate = (TextView) view.findViewById(R.id.textView_rate_detail);
        textView_rate_count = (TextView) view.findViewById(R.id.textView_rate_count_detail);
        try{
            if(DetailActivity.email.equals("isNull")){
                imageView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
            }else {

                listUser = getSetDataFormService.getListUserByEmail(DetailActivity.email);
                textView_fullName.setText(listUser.get(0).getFullName().toString());
                textView_email.setText(listUser.get(0).getEmailLogin().toString());
                textView_phone.setText(listUser.get(0).getPhone().toString());
                imageView.setVisibility(View.INVISIBLE);
            }
        }
        catch (Exception ex) {
            Toast.makeText(getActivity(), "Lỗi k hiển thị thông tin đc.", Toast.LENGTH_LONG).show();
            imageView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
        }
        ;
        // Buttom rate
        isRated();
        getRateCount();
        getRate();
        button_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRating();
            }
        });

        return  view;
    }
    private void rating(int rate){
        RatingUserAsync ratingUserAsync = new RatingUserAsync(getActivity(), DetailActivity.email, rate, DetailActivity.id, button_rate);
        ratingUserAsync.execute();
    }
    private void isRated(){
        if(!DetailActivity.email.equals("")) {
            asyncTaskCheckRated = new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    int stateId = getSetDataFormService.getStateIdProduct(DetailActivity.id);
                    if(stateId ==4) {
                        return getSetDataFormService.checkRated(DetailActivity.email, DetailActivity.id);
                    }else {
                        return false;
                    }
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    button_rate.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                    asyncTaskCheckRated = null;
                }
            };
            asyncTaskCheckRated.execute();
        }
        else {
            button_rate.setVisibility(View.GONE);
        }
    }
    private void getRateCount(){
        asyncTaskRateCount = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                if(!DetailActivity.email.equals("isNull")) {
                    return getSetDataFormService.getRateCount(DetailActivity.email);
                }
                else {
                    return 0;
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                textView_rate_count.setText(integer+"");
                asyncTaskRateCount = null;
            }
        };
        asyncTaskRateCount.execute();
    }
    private void getRate(){
        asyncTaskRateCount = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                if(!DetailActivity.email.equals("isNull")) {
                    return getSetDataFormService.getRate(DetailActivity.email);
                }
                else {
                    return 0;
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                textView_rate.setText(integer+"");
                ratingBar_detail.setRating(integer);
                asyncTaskRateCount = null;
            }
        };
        asyncTaskRateCount.execute();
    }
    private void showDialogRating(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_rating);
        ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar_rating_dialog);
        textView_dialog = (TextView) dialog.findViewById(R.id.textView_state_rating_dialog);
        button_send_dialog = (Button) dialog.findViewById(R.id.button_send_rating_dialog);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int rate = Math.round(rating);
                switch (rate){
                    case 1:
                        textView_dialog.setText("Ghét");
                        return;
                    case 2:
                        textView_dialog.setText("Không thích");
                        return;
                    case 3:
                        textView_dialog.setText("OK");
                        return;
                    case 4:
                        textView_dialog.setText("Thích");
                        return;
                    case 5:
                        textView_dialog.setText("Rất thích");
                        return;
                    default:
                        textView_dialog.setText("");
                        return;
                }
            }
        });
        button_send_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() > 0) {
                    rating(Math.round(ratingBar.getRating()));
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


}
