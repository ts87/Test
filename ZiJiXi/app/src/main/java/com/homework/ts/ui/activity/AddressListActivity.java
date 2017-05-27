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
import com.homework.ts.adapter.AddressAdapter;
import com.homework.ts.model.Address;
import com.homework.ts.model.User;
import com.homework.ts.util.Constant;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ts on 2017/3/25.
 */

public class AddressListActivity extends BaseActivity {
    private String TAG = "AddressListActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mListView;

    public static AddressAdapter adapter;

    private LinearLayout container;

    private int lastItemIndex;
    private int dataCount, count;
    private int load_num = 100;
    private int fromwhere;

    private Intent intent;

    private ProgressWheel progressWheel;
    private TextView textview_tipc;
    private Button addAddressBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_addresses);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        initToolBar("常用地址");

        intent = getIntent();

        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        textview_tipc = (TextView) findViewById(R.id.textview_tipc);
        addAddressBtn = (Button) findViewById(R.id.button_add_address);

        fromwhere = getIntent().getIntExtra("fromwhere",0);

        mListView = (ListView) findViewById(R.id.listview_addresses);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fromwhere == 1){
                    intent.putExtra("id", Constant.allAddressesList.get(position).getId());
                    intent.putExtra("name", Constant.allAddressesList.get(position).getName());
                    intent.putExtra("city", Constant.allAddressesList.get(position).getCity());
                    intent.putExtra("district", Constant.allAddressesList.get(position).getRegion());
                    intent.putExtra("phone", Constant.allAddressesList.get(position).getTel());
                    intent.putExtra("address1", Constant.allAddressesList.get(position).getCommunity());
                    intent.putExtra("address2", Constant.allAddressesList.get(position).getHouse_number());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Intent intent = new Intent(AddressListActivity.this, AddressDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",position);
                    bundle.putInt("id",Constant.allAddressesList.get(position).getId());
                    bundle.putString("name", Constant.allAddressesList.get(position).getName());
                    bundle.putString("city", Constant.allAddressesList.get(position).getCity());
                    bundle.putString("district", Constant.allAddressesList.get(position).getRegion());
                    bundle.putString("phone", Constant.allAddressesList.get(position).getTel());
                    bundle.putString("address1", Constant.allAddressesList.get(position).getCommunity());
                    bundle.putString("address2", Constant.allAddressesList.get(position).getHouse_number());
                    bundle.putString("sex",Constant.allAddressesList.get(position).getSex());
                    bundle.putInt("fromwhere",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
//                        Constant.allAddressesList = new ArrayList<>();
//                        isRefresh = true;
//                        getActivities(count, 1);//!!!!!!!!!!!!!!!!!
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        dataCount = load_num;

        if(User.getInstance().getId() == 0){
            progressWheel.setVisibility(View.GONE);
            textview_tipc.setVisibility(View.VISIBLE);
            textview_tipc.setText("暂未登录");
            addAddressBtn.setVisibility(View.GONE);
        }else{
            Constant.allAddressesList = new ArrayList<>();
            getAddresses();
            addAddressBtn.setVisibility(View.VISIBLE);
        }


        adapter = new AddressAdapter(AddressListActivity.this, Constant.allAddressesList);
        mListView.setAdapter(adapter);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddressDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fromwhere",0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getAddresses(){
//        for(int i=0;i<1; i++) {
//            Address address1 = new Address("ts", "18515287587", "北京市", "海淀区", "上园村3号北京交通大学","学苑公寓6号楼", "");
//            Address address2 = new Address("ts87", "18520899248", "黑龙江省牡丹江市", "", "", "", "男");
//            Constant.allAddressesList.add(address1);
//            Constant.allAddressesList.add(address2);
//        }


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
//                                Constant.allAddressesList = new ArrayList<>();
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("user_address");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Address address = gson.fromJson(jsonArray.getString(i), Address.class);

                                    Constant.allAddressesList.add(address);
                                }

                                textview_tipc.setVisibility(View.GONE);
                                progressWheel.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);

                                Log.i(TAG,"testAddressList==//"+Constant.allAddressesList.toString());
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
