package com.homework.ts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.homework.ts.adapter.OrderClothesInfoAdapter;
import com.homework.ts.adapter.OrderListAdapter;
import com.homework.ts.model.ClothesInfo;
import com.homework.ts.model.OrderBrief;
import com.homework.ts.model.User;
import com.homework.ts.util.Constant;
import com.homework.ts.util.DateUtil;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ts on 2017/4/22.
 */

public class OrderDetailActivity extends BaseActivity {
    private String TAG = "OrderDetailActivity";
    private LinearLayout container;
    private Toolbar toolbar;

    private TextView tvState1, tvState2, tvState3, tvState4, tvState5;
    private TextView tvOrderNumber, tvOrderTime, tvOrderName;
    private TextView tvExpressInfo,tvExpressTime;//物流信息
    private TextView tvClothesAmount;
    private ListView clothesInfoList;
    private TextView tvOrderSumPrice,tvOrderQuan,tvActualPay;//付款信息
//    private TextView tvPayClothesName,tvPayPrice;//实际付款清单
    private TextView tvAddressName, tvAddressPhone, tvAddressDetail;
    private LinearLayout layoutExpress;


    private OrderClothesInfoAdapter adapter;
    private ArrayList<ClothesInfo> orderClothesInfoList = new ArrayList<>();

    private int orderID;
    private Intent intent;
    private OrderBrief order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("订单详情");
//        initMenu(toolbar);

        intent = getIntent();
        order = intent.getParcelableExtra("obj");
        orderID = order.getId();

        initView();

    }

    protected void initView(){
        tvState1 = (TextView) findViewById(R.id.text_state1);
        tvState2 = (TextView) findViewById(R.id.text_state2);
        tvState3 = (TextView) findViewById(R.id.text_state3);
        tvState4 = (TextView) findViewById(R.id.text_state4);
        tvState5 = (TextView) findViewById(R.id.text_state5);
        tvOrderNumber = (TextView) findViewById(R.id.textview_order_number);
        tvOrderTime = (TextView) findViewById(R.id.textview_order_time);
        tvOrderName = (TextView) findViewById(R.id.textview_order_project);
        tvExpressInfo = (TextView) findViewById(R.id.text_express_info);
        tvExpressTime = (TextView) findViewById(R.id.text_express_time);
        tvClothesAmount = (TextView) findViewById(R.id.text_clothes_number);
        clothesInfoList = (ListView) findViewById(R.id.list_clothes_info);
        tvOrderSumPrice = (TextView) findViewById(R.id.text_order_sum_price);
        tvOrderQuan = (TextView) findViewById(R.id.text_order_youhuiquan);
        tvActualPay = (TextView) findViewById(R.id.text_order_actual_pay);
//        tvPayClothesName = (TextView) findViewById(R.id.textview_pay_clothes_name);
//        tvPayPrice = (TextView) findViewById(R.id.textview_pay_price);
        tvAddressName = (TextView) findViewById(R.id.textView_order_address_name);
        tvAddressPhone = (TextView) findViewById(R.id.textView_order_address_phone);
        tvAddressDetail = (TextView) findViewById(R.id.textview_order_address_detail);

        layoutExpress = (LinearLayout) findViewById(R.id.linear_express_info);

//        getClothesInfo();
        getOrderClothesInfo(orderID);
        adapter = new OrderClothesInfoAdapter(OrderDetailActivity.this, orderClothesInfoList);
        clothesInfoList.setAdapter(adapter);

        if(order.getStatus() == 1){
            tvState1.setTextColor(this.getResources().getColor(R.color.red));
        }else if(order.getStatus() == 2){
            tvState2.setTextColor(this.getResources().getColor(R.color.red));
        }else if(order.getStatus() == 3){
            tvState3.setTextColor(this.getResources().getColor(R.color.red));
        }else if(order.getStatus() == 4){
            tvState4.setTextColor(this.getResources().getColor(R.color.red));
        }else if(order.getStatus() == 5){
            tvState5.setTextColor(this.getResources().getColor(R.color.red));
        }
        tvOrderNumber.setText("订单编号："+String.valueOf(order.getId()));
        tvOrderTime.setText("取件时间："+DateUtil.DateFormat(order.getCreated_at()));
        tvOrderName.setText("洗衣项目："+order.getCategory_name());
        tvExpressTime.setText(DateUtil.DateFormat(order.getDate()));
        tvExpressInfo.setText(order.getWaybills_desc());
        tvOrderQuan.setText("0.00元");
        tvAddressName.setText(order.getName());
        tvAddressPhone.setText(order.getTel());
        tvAddressDetail.setText(order.getCity()+order.getRegion()+order.getCommunity()+order.getHouse_number());
        tvOrderSumPrice.setText(String.valueOf(order.getTotal_price())+".00元");
        tvActualPay.setText(String.valueOf(order.getTotal_price())+".00元");


        layoutExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, ExpressListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("orderID",orderID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void getClothesInfo(){
        ClothesInfo c1 = new ClothesInfo("衣服",3);
        ClothesInfo c2 = new ClothesInfo("窗帘",1);
        orderClothesInfoList.add(c1);
        orderClothesInfoList.add(c2);
    }

    public void getOrderClothesInfo(int orderID){

        String url = Constant.MY_UTL + "items/get_items.json?order_id=" + orderID;

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
                                Constant.allOrdersList = new ArrayList<>();
                                int clothesAmount = 0;
                                int clothesSumPrice = 0;
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("items");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    ClothesInfo clothes = gson.fromJson(jsonArray.getString(i), ClothesInfo.class);

                                    orderClothesInfoList.add(clothes);
                                    clothesAmount += clothes.getAmount();
                                    clothesSumPrice += clothes.getPrice();
                                }


                                adapter = new OrderClothesInfoAdapter(OrderDetailActivity.this, orderClothesInfoList);
                                clothesInfoList.setAdapter(adapter);

                                tvClothesAmount.setText("共"+String.valueOf(clothesAmount)+"件衣物");
//                                tvOrderSumPrice.setText(String.valueOf(clothesSumPrice)+"元");
//                                tvActualPay.setText(String.valueOf(clothesSumPrice)+"元");
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
