package com.onesimply.sonnv.androidtransportgcm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.onesimply.sonnv.androidtransportgcm.asyncs.SPreceivedAsyncTask;
import com.onesimply.sonnv.androidtransportgcm.entities.product;
import com.onesimply.sonnv.androidtransportgcm.tool.CustomListPostProductAllAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedProductFragment extends Fragment {

    public ArrayList<product> dsProduct;
    public CustomListPostProductAllAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_received_product, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.listView_received_product);
        dsProduct = new ArrayList<product>();
        adapter  = new CustomListPostProductAllAdapter(getActivity(), dsProduct);
        listView.setAdapter(adapter);
        getProduct();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                product pd = (product) o;
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("email", pd.getEmailLogin());
                intent.putExtra("id", pd.getId()+"");
                startActivity(intent);
                Toast.makeText(getActivity(), "Selected :" + " " + pd.getEmailLogin() + pd.getId(), Toast.LENGTH_LONG).show();
            }
        });
        return  view;
    }
    private void getProduct(){
        SPreceivedAsyncTask SPreceivedAsyncTask = new SPreceivedAsyncTask(this);
        SPreceivedAsyncTask.execute();
    }

}
