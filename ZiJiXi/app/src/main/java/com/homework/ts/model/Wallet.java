package com.homework.ts.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ts on 2017/5/13.
 */

public class Wallet implements Parcelable {
    private int id;
    private int kind;
    private int real_money;
    private int fake_money;
    private String loggable_type;
    private int loggable_id;
    private String created_at;


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

    public int getReal_money() {
        return real_money;
    }

    public void setReal_money(int real_money) {
        this.real_money = real_money;
    }

    public int getFake_money() {
        return fake_money;
    }

    public void setFake_money(int ake_money) {
        this.fake_money = ake_money;
    }

    public String getLoggable_type() {
        return loggable_type;
    }

    public void setLoggable_type(String loggable_type) {
        this.loggable_type = loggable_type;
    }

    public int getLoggable_id() {
        return loggable_id;
    }

    public void setLoggable_id(int loggable_id) {
        this.loggable_id = loggable_id;
    }

    protected Wallet(Parcel in) {
        id = in.readInt();
        kind = in.readInt();
        real_money = in.readInt();
        fake_money = in.readInt();
        loggable_id = in.readInt();
        loggable_type = in.readString();
        created_at = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(kind);
        dest.writeInt(real_money);
        dest.writeInt(fake_money);
        dest.writeInt(loggable_id);
        dest.writeString(loggable_type);
        dest.writeString(created_at);
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
