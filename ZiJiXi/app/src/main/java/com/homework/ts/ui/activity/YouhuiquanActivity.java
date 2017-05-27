package com.homework.ts.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.homework.ts.adapter.AddressAdapter;
import com.homework.ts.adapter.YouhuiquanAdapter;
import com.homework.ts.model.Address;
import com.homework.ts.model.CouponInfo;
import com.homework.ts.model.CouponOrders;
import com.homework.ts.model.CouponOrigin;
import com.homework.ts.model.User;
import com.homework.ts.model.Youhuiquan;
import com.homework.ts.util.Constant;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ts on 2017/5/6.
 */

public class YouhuiquanActivity extends BaseActivity {
    private String TAG = "YouhuiquanActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mListView;

    public static YouhuiquanAdapter adapter;

    private LinearLayout container;

    private AlertDialog mDialog = null;
    private int lastItemIndex;
    private int dataCount, count;
    private int load_num = 100;
    private int fromwhere;

    private Intent intent;

    private ProgressWheel progressWheel;
    private TextView textview_tipc;
    private Button addYouhuiquanBtn;
    private int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiquan);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        initToolBar("优惠券");

        intent = getIntent();

        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        textview_tipc = (TextView) findViewById(R.id.textview_tipc);
        addYouhuiquanBtn = (Button) findViewById(R.id.button_add_youhuiquan);

        fromwhere = getIntent().getIntExtra("fromwhere",0);
        price = getIntent().getIntExtra("price",0);
        Log.i(TAG,"price="+price);

        mListView = (ListView) findViewById(R.id.listview_youhuiquan);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fromwhere == 1){
                    Log.i(TAG,"price="+price+"//getPremise=="+Constant.allYouhuiquanList.get(position).getCoupon_orders().getPremise());
                    if(Constant.allYouhuiquanList.get(position).getCoupon_orders().getPremise()<=price){
                        intent.putExtra("price", Constant.allYouhuiquanList.get(position).getCoupon_orders().getDiscount());
                        intent.putExtra("coupon_id",Constant.allYouhuiquanList.get(position).getCoupon_info().getId());
                        intent.putExtra("order_promotion_id",Constant.allYouhuiquanList.get(position).getCoupon_orders().getId());
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        showToast("该优惠券不能使用～订单总价没有达到优惠标准哦～");
                    }

                }else{

                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Constant.allYouhuiquanList = new ArrayList<>();
                        getYouhuiquan();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        dataCount = load_num;

        getYouhuiquan();

        adapter = new YouhuiquanAdapter(YouhuiquanActivity.this, Constant.allYouhuiquanList);
        mListView.setAdapter(adapter);

        addYouhuiquanBtn.setVisibility(View.GONE);
//        addYouhuiquanBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAddDialog(YouhuiquanActivity.this);
//
//            }
//        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void showAddQuanDialog() {
        new AlertDialog.Builder(this).setTitle("请输入优惠券兑换码")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(new EditText(this)).setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .show();
    }


/*    private void showAddDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View textEntryView = inflater.inflate(
                R.layout.dialoglayout, null);
        final EditText edtInput=(EditText)textEntryView.findViewById(R.id.edtInput);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
//        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("请输入优惠券兑换码");
        builder.setView(textEntryView);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
//                        setTitle(edtInput.getText());
                        Youhuiquan quan = new Youhuiquan("新活动","满88可用","8.8","2017.5.1 - 2017.5.10");
                        Constant.allYouhuiquanList.add(quan);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        builder.show();
    }*/

    public void getYouhuiquan() {

            String url = Constant.MY_UTL + "coupons/get_user_coupons.json?user_id=" + User.getInstance().getId();

            Log.i("TAG", url);

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            int result = 0;
                            try {
//                                result = response.getInt("status");

//                                Log.i(TAG, "/" + response.toString());

//                                if (result == 200) {
                                    Constant.allYouhuiquanList = new ArrayList<>();
                                    Gson gson = new Gson();
                                    JSONArray jsonArray = response.getJSONArray("coupons");

                                Constant.coupon_number = jsonArray.length();
                                Log.i(TAG, "/" + response.toString()+"//"+Constant.coupon_number);

                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        Youhuiquan coupon = gson.fromJson(jsonArray.getString(i), Youhuiquan.class);
//                                        Constant.allYouhuiquanList.add(coupon);

                                        JSONObject jo = jsonArray.getJSONObject(i);
                                        CouponInfo coupon_info = gson.fromJson(jo.get("coupon_info").toString(), CouponInfo.class);
//                                        CouponOrders coupon_orders = gson.fromJson(jo.get("coupon_orders").toString(), CouponOrders.class);
                                        CouponOrigin coupon_origin = gson.fromJson(jo.get("coupon_origin").toString(), CouponOrigin.class);

//                                        JSONArray jsonArray1 = jo.getJSONArray("coupon_info");
                                        JSONArray jsonArray2 = jo.getJSONArray("coupon_orders");
//                                        JSONArray jsonArray3 = jo.getJSONArray("coupon_origin");
//                                        CouponInfo coupon_info = gson.fromJson(jsonArray1.getString(0), CouponInfo.class);
                                        CouponOrders coupon_orders = gson.fromJson(jsonArray2.getString(0), CouponOrders.class);
//                                        CouponOrigin coupon_origin = gson.fromJson(jsonArray3.getString(0), CouponOrigin.class);
                                        Youhuiquan coupon = new Youhuiquan();
                                        coupon.setCoupon_info(coupon_info);
                                        coupon.setCoupon_orders(coupon_orders);
                                        coupon.setCoupon_origin(coupon_origin);
                                        Constant.allYouhuiquanList.add(coupon);
                                    }

                                    if(Constant.allYouhuiquanList.size()==0){
                                        textview_tipc.setVisibility(View.VISIBLE);
                                        textview_tipc.setText("暂时没有优惠券哦～");
                                        progressWheel.setVisibility(View.GONE);
                                    }

                                    adapter = new YouhuiquanAdapter(YouhuiquanActivity.this, Constant.allYouhuiquanList);
                                    mListView.setAdapter(adapter);

                                    textview_tipc.setVisibility(View.GONE);
                                    progressWheel.setVisibility(View.GONE);
                                    mListView.setVisibility(View.VISIBLE);

//                                }
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
