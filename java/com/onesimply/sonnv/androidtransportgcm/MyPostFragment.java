package com.onesimply.sonnv.androidtransportgcm;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.asyncs.PTmyPostAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.tool.CustomListMyPostProductAdapter;
import com.onesimply.sonnv.androidtransportgcm.tool.GetSetDataFormService;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostFragment extends Fragment {

    private GetSetDataFormService getSetDataFormService;
    public MyPostFragment() {
        // Required empty public constructor
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    ProgressBar progressBar;
    private ListView listView;
    public String email;
    public ArrayList<product> dsProduct;
    public CustomListMyPostProductAdapter adapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_post, container, false);
        ///simpleCustomView = new SimpleCustomView(getActivity(), getData(), 1);
        //View view = simpleCustomView;

        listView = (ListView) view.findViewById(R.id.listMyPostProduct);
        getSetDataFormService = new GetSetDataFormService();
        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra("user");
        dsProduct = new ArrayList<product>();
        adapter = new CustomListMyPostProductAdapter(getActivity(), dsProduct);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                product pd = (product) o;
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("email", pd.getEmailCarrier().toString());
                intent.putExtra("id", pd.getId() + "");
                startActivity(intent);
                Toast.makeText(getActivity(), "Selected :" + " " + pd.getName(), Toast.LENGTH_LONG).show();
            }
        });
        getProduct();

        return view;
    }
    private void getProduct(){
        PTmyPostAsyncTask PTmyPostAsyncTask = new PTmyPostAsyncTask(this);
        PTmyPostAsyncTask.execute();
    }
}
