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

import com.google.gson.Gson;
import com.homework.ts.adapter.AddressAdapter;
import com.homework.ts.model.Address;
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

    private AddressAdapter adapter;

    private LinearLayout container;

    private int lastItemIndex;
    private int dataCount, count;
    private int load_num = 100;

    private boolean isRefresh = false;


    private ProgressWheel progressWheel;
    private TextView textview_tipc;
    private Button addAddressBtn;

    private ArrayList<Address> allAddressesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_addresses);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        initToolBar("常用地址");

        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        textview_tipc = (TextView) findViewById(R.id.textview_tipc);
        addAddressBtn = (Button) findViewById(R.id.button_add_address);

        mListView = (ListView) findViewById(R.id.listview_addresses);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AddressListActivity.this, AddressDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", allAddressesList.get(position).getName());
                bundle.putString("city", allAddressesList.get(position).getCity());
                bundle.putString("district", allAddressesList.get(position).getDistrict());
                bundle.putString("phone", allAddressesList.get(position).getPhone());
                bundle.putString("address1", allAddressesList.get(position).getAddress1());
                bundle.putString("address2", allAddressesList.get(position).getAddress2());
                bundle.putInt("sex",allAddressesList.get(position).getSex());
                bundle.putInt("fromwhere",1);
                intent.putExtras(bundle);
                startActivity(intent);

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
                        allAddressesList = new ArrayList<>();
                        isRefresh = true;
//                        getActivities(count, 1);//!!!!!!!!!!!!!!!!!
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        dataCount = load_num;

        getAddresses();


        adapter = new AddressAdapter(AddressListActivity.this, allAddressesList);
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
        for(int i=0;i<3; i++) {
            Address address1 = new Address("ts", "18515287587", "北京市", "海淀区", "上园村3号北京交通大学","学苑公寓6号楼", -1);
            Address address2 = new Address("ts87", "18520899248", "黑龙江省牡丹江市", "", "", "", 1);
            allAddressesList.add(address1);
            allAddressesList.add(address2);
        }

        progressWheel.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

}
