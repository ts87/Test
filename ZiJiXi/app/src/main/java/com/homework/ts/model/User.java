package com.homework.ts.model;

/**
 * Created by ts on 2017/4/14.
 */

public class User {
    private static User user;

    public User(){

    }

    public static User getInstance() {
        if(user == null)
            user = new User();
        return user;
    }

    public static User setInstance (){
        user = null;
        return user;
    }

    public static User newInstance(User OXUser) {

        return user = OXUser;
    }



    private int user_id = 0;
    private String user_name = null;
    private boolean isLogin = false;
    private String phone;



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
