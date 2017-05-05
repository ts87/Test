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



    private int id = 0;
    private String mobile;
    private String email;
    private String encrypted_password;
    private String name = null;
    private boolean isLogin = false;
//    private String phone;






    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
