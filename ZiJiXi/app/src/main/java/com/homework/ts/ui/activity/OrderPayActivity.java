package com.homework.ts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.homework.ts.adapter.OrderClothesInfoAdapter;
import com.homework.ts.model.ClothesInfo;
import com.homework.ts.model.OrderBrief;
import com.homework.ts.model.User;
import com.homework.ts.ui.fragment.MyFragment;
import com.homework.ts.ui.fragment.OrderFragment;
import com.homework.ts.util.Constant;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.MainActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ts on 2017/5/6.
 */

public class OrderPayActivity extends BaseActivity {
    private String TAG = "OrderPayActivity";
    private LinearLayout container, coupon_linear;
    private Toolbar toolbar;
    private TextView tvClothesName, tvClothesNum, tvClothesPrice, tvActualPrice;
    private TextView couponNumber,couponZhang;
    private CheckBox cb_use_balance;
    private OrderBrief order;
    private ArrayList<ClothesInfo> orderClothesInfoList = new ArrayList<>();
    private int use_balance,total_price, order_id;
    private Button payButton;
    private Double couponPrice;
    private int coupon_id,order_promotion_id;
    private boolean useCoupon = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("订单支付");
//        initMenu(toolbar);

        order = getIntent().getParcelableExtra("obj");
        order_id = order.getId();
        getOrderClothesInfo(order.getId());

        initView();
    }

    protected void initView(){
        cb_use_balance = (CheckBox) findViewById(R.id.checkbox_use_balance);
        tvClothesName = (TextView) findViewById(R.id.text_clothes_name);
        tvClothesNum = (TextView) findViewById(R.id.text_clothes_number);
        tvClothesPrice = (TextView) findViewById(R.id.text_order_sum_price);
        tvActualPrice = (TextView) findViewById(R.id.text_order_actual_pay);
        payButton = (Button) findViewById(R.id.button_order_pay);
        coupon_linear = (LinearLayout) findViewById(R.id.linear_coupon);
        couponNumber = (TextView) findViewById(R.id.text_youhuiquan_number);
        couponZhang = (TextView) findViewById(R.id.text_youhuiquan_zhang);

        couponNumber.setText(String.valueOf(Constant.coupon_number));

        tvClothesName.setText(order.getName());

        Log.i(TAG,"balance="+Constant.balance);


        cb_use_balance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                showToast(buttonView?"选中了":"取消了选中");
                if(isChecked){
                    //使用余额
                }
            }
        });

        coupon_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.coupon_number != 0){
                    Intent intent = new Intent(OrderPayActivity.this, YouhuiquanActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("fromwhere",1);
                    bundle.putInt("price",total_price);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payOrder(order_id);
            }
        });
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
                                total_price = clothesSumPrice;

                                tvClothesNum.setText("x"+String.valueOf(clothesAmount));
                                tvClothesPrice.setText(String.valueOf(clothesSumPrice)+"元");
                                tvActualPrice.setText(String.valueOf(clothesSumPrice)+"元");

                                if(Constant.balance == 0){
                                    cb_use_balance.setVisibility(View.INVISIBLE);
                                }else{
                                    cb_use_balance.setVisibility(View.VISIBLE);
                                    if(Constant.balance <= total_price){
                                        cb_use_balance.setText("使用余额¥"+Constant.balance);
                                    }else{
                                        cb_use_balance.setText("使用余额"+total_price);
                                    }
                                }
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

    private void payOrder(final int order_id){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String httpurl = Constant.MY_UTL + "users/pay";

        Map<String, String> params = new HashMap<String, String>();
        params.put("order_id",String.valueOf(order_id));
        params.put("user_id", String.valueOf(User.getInstance().getId()));
        if(useCoupon==true){
            params.put("coupon_id", String.valueOf(coupon_id));
            params.put("order_promotion_id", String.valueOf(order_promotion_id));
        }

        JSONObject jsonObject = new JSONObject(params);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, httpurl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response -> " + response.toString());

                        String result = null;
                        try {
                            result = response.getString("status");
                            Log.i("Result", response.toString() + "//" );


                            if (result.equals("200")) {
                                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();

                                for(int i=0; i<OrderFragment.ordersList.size();i++) {
                                    if (OrderFragment.ordersList.get(i).getId() == order_id) {
                                        OrderFragment.ordersList.get(i).setStatus(4);
                                    }
                                }
                                OrderFragment.adapter.notifyDataSetChanged();
                                Constant.balance -= total_price;
                                MyFragment.textMoney.setText(String.valueOf(Constant.balance));

                                Constant.coupon_number -= 1;
                                MyFragment.textQuan.setText(String.valueOf(Constant.coupon_number));
                                finish();
                            }

                            if (result.equals("400")) {
                                Toast.makeText(getApplicationContext(), "您的账户余额不足", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String name = b.getString("name");
                Log.i(TAG,"传回"+name);
                couponPrice = b.getDouble("price");
                order_promotion_id = b.getInt("order_promotion_id");
                coupon_id = b.getInt("coupon_id");
                couponNumber.setTextColor(this.getResources().getColor(R.color.red));
                couponNumber.setText("-¥"+String.valueOf(couponPrice));
                couponZhang.setVisibility(View.INVISIBLE);
//                finish();
                useCoupon = true;
                break;
            default:
                break;
        }
    }
}
