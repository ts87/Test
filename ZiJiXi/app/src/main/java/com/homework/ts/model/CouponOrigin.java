package com.homework.ts.model;

/**
 * Created by ts on 2017/5/20.
 */

public class CouponOrigin {//优惠券源数据
    private int id;
    private String name;
    private int validity_type;
    private String valid_from;
    private String valid_to;
    private int fixed_begin_term;
    private int fixed_term;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValidity_type() {
        return validity_type;
    }

    public void setValidity_type(int validity_type) {
        this.validity_type = validity_type;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    public int getFixed_begin_term() {
        return fixed_begin_term;
    }

    public void setFixed_begin_term(int fixed_begin_term) {
        this.fixed_begin_term = fixed_begin_term;
    }

    public int getFixed_term() {
        return fixed_term;
    }

    public void setFixed_term(int fixed_term) {
        this.fixed_term = fixed_term;
    }
}
