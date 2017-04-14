package com.homework.ts.util;

import android.app.Activity;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ts on 2017/3/25.
 */

public class Constant {
    public static List<Activity> finishActivities = new ArrayList<Activity>();
    public static ArrayList<String> adURL = new ArrayList<>();
    public static RequestQueue queue;
    public static final String MY_UTL = "http://w/api/";//正式
    public static boolean isLogin = false;
}
