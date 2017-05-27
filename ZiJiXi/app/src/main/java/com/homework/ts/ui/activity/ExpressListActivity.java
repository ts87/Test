package com.homework.ts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.homework.ts.adapter.ExpressAdapter;
import com.homework.ts.model.Express;
import com.homework.ts.model.User;
import com.homework.ts.util.Constant;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ts on 2017/5/7.
 */

public class ExpressListActivity extends BaseActivity {
    private String TAG = "ExpressListActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mListView;

    public static ExpressAdapter adapter;

    private LinearLayout container;

    private int lastItemIndex;
    private int dataCount, count;
    private int load_num = 100;

    private Intent intent;
    private int orderID;

    private ProgressWheel progressWheel;
    private TextView textview_tipc;
    private List<Express> expressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_list);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        initToolBar("物流信息");


        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        textview_tipc = (TextView) findViewById(R.id.textview_tipc);

        mListView = (ListView) findViewById(R.id.listview_express);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
//                        Constant.allAddressesList = new ArrayList<>();
//                        isRefresh = true;
                        expressList.clear();
                        getExpress(orderID);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        intent = getIntent();
        orderID = intent.getIntExtra("orderID",0);

        dataCount = load_num;


        getExpress(orderID);
        adapter = new ExpressAdapter(ExpressListActivity.this, expressList);
        mListView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getExpress(int orderID) {

        String url = Constant.MY_UTL + "waybills/get_waybills.json?order_id=" + orderID;

        Log.i("TAG", url);

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        int result = 0;
                        try {
                            result = response.getInt("status");

                            Log.i(TAG, "/" + response.toString());

                            if (result == 200) {
//                                Constant.allAddressesList = new ArrayList<>();
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("waybills");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Express express = gson.fromJson(jsonArray.getString(i), Express.class);

                                    expressList.add(express);
                                }

                                adapter = new ExpressAdapter(ExpressListActivity.this, expressList);
                                mListView.setAdapter(adapter);

                                textview_tipc.setVisibility(View.GONE);
                                progressWheel.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);
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