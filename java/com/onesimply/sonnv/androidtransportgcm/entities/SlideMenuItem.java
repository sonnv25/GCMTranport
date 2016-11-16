package com.onesimply.sonnv.androidtransportgcm.entities;

/**
 * Created by N on 07/03/2016.
 */
public class SlideMenuItem {
    private String title;
    private int icon;
    private String count="0";
    private boolean isCountVisible = false;

    public SlideMenuItem(){}
    public SlideMenuItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public SlideMenuItem(String title, String count, int icon, boolean isCountVisible) {
        this.title = title;
        this.count = count;
        this.icon = icon;
        this.isCountVisible = isCountVisible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isCountVisible() {
        return isCountVisible;
    }

    public void setIsCountVisible(boolean isCountVisible) {
        this.isCountVisible = isCountVisible;
    }
}
