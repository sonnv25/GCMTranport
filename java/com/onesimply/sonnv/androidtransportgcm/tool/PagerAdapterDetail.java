package com.onesimply.sonnv.androidtransportgcm.tool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.onesimply.sonnv.androidtransportgcm.DetailProductFragment;
import com.onesimply.sonnv.androidtransportgcm.DetailUserLoginFragment;

/**
 * Created by N on 27/02/2016.
 */
public class PagerAdapterDetail extends FragmentStatePagerAdapter {
    int mNumOfTab;
    public PagerAdapterDetail(FragmentManager fm, int mNumOfTab) {
        super(fm);
        this.mNumOfTab = mNumOfTab;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DetailProductFragment tab1 = new DetailProductFragment();
                return tab1;
            case 1:
                DetailUserLoginFragment tab2 = new DetailUserLoginFragment();
                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTab;
    }
}
