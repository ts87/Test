package com.homework.ts.util;

import android.app.Activity;

import com.android.volley.RequestQueue;
import com.homework.ts.model.Address;
import com.homework.ts.model.OrderBrief;
import com.homework.ts.model.Wallet;
import com.homework.ts.model.Youhuiquan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ts on 2017/3/25.
 */

public class Constant {
    public static List<Activity> finishActivities = new ArrayList<Activity>();
    public static ArrayList<String> adURL = new ArrayList<>();
    public static RequestQueue queue;
    public static final String MY_UTL = "http://10.0.2.2:3000/";//模拟器
//    public static final String MY_UTL = "http://172.20.10.3:3000/";
    public static boolean isLogin = false;
    public static float lontitude = 116.355089f;
    public static float latitude = 39.960314f;
    public static ArrayList<Address> allAddressesList = new ArrayList<>();
    public static ArrayList<OrderBrief> allOrdersList = new ArrayList<>();
    public static ArrayList<Youhuiquan> allYouhuiquanList = new ArrayList<>();
    public static ArrayList<Wallet> allWalletList = new ArrayList<>();
    public static int balance;//余额
    public static int coupon_number;
}
