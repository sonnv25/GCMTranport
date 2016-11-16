package com.onesimply.sonnv.androidtransportgcm.entities;

import java.util.ArrayList;

/**
 * Created by N on 09/03/2016.
 */
public class ArrayClass {
    public static String[] user_role ={"","Người đăng", "Người vận chuyển", "Người nhân", "Tất cả (Đăng, Nhận và vận chuyển sản phẩm)"};
    public static ArrayList<String> loaction_address;
    public static double lat =0;
    public static double lng =0;
    public static String[] type_post={"Đăng nhanh", "Chọn người vận chuyển", "Lưu"};
    public static String[] time_set = {"5 phút","10 phút", "20 phút", "30 phút", "Thêm hẹn giờ"};
    public static boolean timer = false;
    public static int time;
    public static int hour, minute, second;
}
