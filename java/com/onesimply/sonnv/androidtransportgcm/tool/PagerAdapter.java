package com.onesimply.sonnv.androidtransportgcm.tool;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.onesimply.sonnv.androidtransportgcm.MyPostFragment;
import com.onesimply.sonnv.androidtransportgcm.PostFragment;
import com.onesimply.sonnv.androidtransportgcm.ProductTimeoutFragment;

/**
 * Created by N on 22/02/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter{

    int mNumOfTab;
    public PagerAdapter(FragmentManager fm, int mNumOfTab){
        super(fm);
        this.mNumOfTab = mNumOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PostFragment tab1 = new PostFragment();
                return tab1;
            case 1:
                ProductTimeoutFragment tab3 = new ProductTimeoutFragment();
                return  tab3;
            case 2:
                MyPostFragment tab2 = new MyPostFragment();
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
