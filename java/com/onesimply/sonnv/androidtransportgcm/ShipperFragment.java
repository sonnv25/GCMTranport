package com.onesimply.sonnv.androidtransportgcm;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.onesimply.sonnv.androidtransportgcm.tool.PagerAdapterInShipper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShipperFragment extends Fragment {

    private FragmentActivity fragmentActivity;
    public ShipperFragment() {
        // Required empty public constructor
    }
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Đang tải dữ liệu...");
        mProgressDialog.setMessage("Vui lòng chờ ...");
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        boolean a = false;
        if(a){
            View view = new View(getActivity());
            mProgressDialog.show();
            return view;
        }else {

            View view = inflater.inflate(R.layout.fragment_shipper, container, false);
            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_shipper);
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_product_infomation)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.title_product_recieved)));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager_shipper);
            PagerAdapterInShipper adapter = new PagerAdapterInShipper(fragmentActivity.getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            mProgressDialog.dismiss();
            return view;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        fragmentActivity = (FragmentActivity) activity;
        super.onAttach(activity);
    }

}
