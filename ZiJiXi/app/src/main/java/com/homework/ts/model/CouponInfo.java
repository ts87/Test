package com.homework.ts.model;

/**
 * Created by ts on 2017/5/20.
 */

public class CouponInfo {//用户优惠券信息
    private int id;
    private int coupon_list_id;
    private String vaild_from;
    private String valid_to;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoupon_list_id() {
        return coupon_list_id;
    }

    public void setCoupon_list_id(int coupon_list_id) {
        this.coupon_list_id = coupon_list_id;
    }

    public String getVaild_from() {
        return vaild_from;
    }

    public void setVaild_from(String vaild_from) {
        this.vaild_from = vaild_from;
    }

    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }
}
