package com.homework.ts.model;

import java.util.ArrayList;
import com.homework.ts.model.CouponInfo;
import com.homework.ts.model.CouponOrders;
import com.homework.ts.model.CouponOrigin;

/**
 * Created by ts on 2017/5/6.
 */

public class Youhuiquan {
//    private String coupon_info;//用户优惠券信息
//    private String coupon_orders;//优惠规则
//    private String coupon_origin;//优惠券源数据

//    private ArrayList<CouponInfo> coupon_info = new ArrayList<>();//用户优惠券信息
//    private ArrayList<CouponOrders> coupon_orders = new ArrayList<>();//优惠规则
//    private ArrayList<CouponOrigin> coupon_origin = new ArrayList<>();//优惠券源数据

    private CouponInfo coupon_info = new CouponInfo();//用户优惠券信息
    private CouponOrders coupon_orders = new CouponOrders();//优惠规则
    private CouponOrigin coupon_origin = new CouponOrigin();//优惠券源数据

    public CouponInfo getCoupon_info() {
        return coupon_info;
    }

    public void setCoupon_info(CouponInfo coupon_info) {
        this.coupon_info = coupon_info;
    }

    public CouponOrders getCoupon_orders() {
        return coupon_orders;
    }

    public void setCoupon_orders(CouponOrders coupon_orders) {
        this.coupon_orders = coupon_orders;
    }

    public CouponOrigin getCoupon_origin() {
        return coupon_origin;
    }

    public void setCoupon_origin(CouponOrigin coupon_origin) {
        this.coupon_origin = coupon_origin;
    }
}
