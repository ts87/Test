package com.homework.ts.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.homework.ts.model.Address;
import com.homework.ts.model.User;
import com.homework.ts.util.Constant;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ts on 2017/5/5.
 */

public class AppointActivity extends BaseActivity {
    private String TAG = "AppointActivity";
    private LinearLayout container;
    private Toolbar toolbar;
    private TextView addressName, addressPhone, addressDetail, textview_tips, tvClothesType, tvGetTime;
    private RelativeLayout layoutChooseTime, layoutChooseAddress;
    private Button button;
    private Intent intent;
    public  ArrayList<Address> addressesList = new ArrayList<>();
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    int mYear, mMonth, mDay, mHour, mMinute;
//    final int DATE_DIALOG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("预约取件");

        intent = getIntent();
        getAddresses();
        initView();

    }

    public void initView(){
        addressName = (TextView) findViewById(R.id.textView_address_name);
        addressPhone = (TextView) findViewById(R.id.textView_address_phone);
        addressDetail = (TextView) findViewById(R.id.textview_address_detail);

        layoutChooseAddress = (RelativeLayout) findViewById(R.id.relative_address);
        layoutChooseTime = (RelativeLayout) findViewById(R.id.choose_time);
        textview_tips = (TextView) findViewById(R.id.textview_tips);//备注
        tvClothesType = (TextView) findViewById(R.id.appointment_clothes_type);
        tvGetTime = (TextView) findViewById(R.id.get_clothes_time);
        button = (Button) findViewById(R.id.appointment_button);

        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);

//        if(addressesList.size() != 0){
//            addressName.setText(addressesList.get(0).getName());
//            addressPhone.setText(addressesList.get(0).getTel());
//            addressDetail.setText(addressesList.get(0).getCity()+addressesList.get(0).getRegion()
//                    +addressesList.get(0).getCommunity()+addressesList.get(0).getHouse_number());
//        }else{
//            addressName.setText("请选择地址");
//            addressPhone.setText("");
//            addressDetail.setText("");
//        }

        layoutChooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointActivity.this, AddressListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fromwhere",1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        layoutChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.setVisibility(View.VISIBLE);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("预约成功！请等待小e上门确认价格并付款");
                intent.putExtra("str1", "success");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mHour = ca.get(Calendar.HOUR);
        mMinute = ca.get(Calendar.MINUTE);

        mDatePicker.init(mYear, mMonth, mDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int month,
                                      int day) {
                AppointActivity.this.mYear = mYear;
                AppointActivity.this.mMonth = mMonth;
                AppointActivity.this.mDay = mDay;
                // 显示当前日期、时间
                showDate(mYear, mMonth, mDay, mHour, mMinute);

                mDatePicker.setVisibility(View.GONE);
                mTimePicker.setVisibility(View.VISIBLE);
            }
        });

        // 为TimePicker指定监听器
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker arg0, int hour, int minute) {
                AppointActivity.this.mHour = hour;
                AppointActivity.this.mMinute = minute;
                // 显示当前日期、时间
                showDate(mYear, mMonth, mDay, mHour, mMinute);
                mTimePicker.setVisibility(View.GONE);
                mDatePicker.setVisibility(View.GONE);
            }
        });

    }

    private void showDate(int year, int month, int day, int hour, int minute){
        tvGetTime.setText(new StringBuffer().append(year).append("-").append(month + 1).append("-").append(day).append(" ").append(hour).append(":").append(minute));
    }

    public void getAddresses(){
        String url = Constant.MY_UTL + "addresses/get_user_address.json?user_id=" + User.getInstance().getId();

        Log.i("TAG", url);

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        int result = 0;
                        try {
                            result = response.getInt("status");

                            Log.i(TAG,   "/" + response.toString());

                            if (result == 200) {
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("user_address");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Address address = gson.fromJson(jsonArray.getString(i), Address.class);

//                                    Constant.allAddressesList.add(address);
                                    addressesList.add(address);

                                }

                                if(addressesList.size() != 0){
                                    addressName.setText(addressesList.get(0).getName());
                                    addressPhone.setText(addressesList.get(0).getTel());
                                    addressDetail.setText(addressesList.get(0).getCity()+addressesList.get(0).getRegion()
                                            +addressesList.get(0).getCommunity()+addressesList.get(0).getHouse_number());
                                }else{
                                    addressName.setText("请选择地址");
                                    addressPhone.setText("");
                                    addressDetail.setText("");
                                }

//                                textview_tipc.setVisibility(View.GONE);
//                                progressWheel.setVisibility(View.GONE);
//                                mListView.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Json e", e.getMessage());
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Constant.queue.add(jsonObjRequest);

    }
}
