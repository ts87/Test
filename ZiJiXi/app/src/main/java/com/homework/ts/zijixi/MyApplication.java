package com.homework.ts.zijixi;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.homework.ts.model.User;
import com.homework.ts.util.Constant;

/**
 * Created by ts on 2017/4/14.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
//        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
//        String userJson = sp.getString("json", "");
//        if (!userJson.equals("")) {
//            Gson gson = new Gson();
//            User user = gson.fromJson(userJson.toString(), User.class);
//            User.newInstance(user);
//            Constant.isLogin = true;
//        }
    }
}
