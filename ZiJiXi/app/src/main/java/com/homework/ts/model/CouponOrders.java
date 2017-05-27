package com.homework.ts.model;

/**
 * Created by ts on 2017/5/20.
 */

public class CouponOrders {//优惠规则
    private int id;
    private int kind;
    private double discount;
    private int premise;
    private int coupon_list_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getPremise() {
        return premise;
    }

    public void setPremise(int premise) {
        this.premise = premise;
    }

    public int getCoupon_list_id() {
        return coupon_list_id;
    }

    public void setCoupon_list_id(int coupon_list_id) {
        this.coupon_list_id = coupon_list_id;
    }
}
