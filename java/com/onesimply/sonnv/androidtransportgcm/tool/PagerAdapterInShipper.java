package com.onesimply.sonnv.androidtransportgcm.tool;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.onesimply.sonnv.androidtransportgcm.ProductInfoFragment;
import com.onesimply.sonnv.androidtransportgcm.ReceivedProductFragment;

/**
 * Created by N on 07/03/2016.
 */
public class PagerAdapterInShipper extends FragmentStatePagerAdapter {
    private int numOfTag;
    public PagerAdapterInShipper(FragmentManager fragmentManager, int numOfTag){
        super(fragmentManager);
        this.numOfTag = numOfTag;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProductInfoFragment tab1 = new ProductInfoFragment();
                return tab1;
            case 1:
                ReceivedProductFragment tab2 = new ReceivedProductFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTag;
    }
}
