package com.homework.ts.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.homework.ts.adapter.AddressAdapter;
import com.homework.ts.adapter.OrderListAdapter;
import com.homework.ts.model.Address;
import com.homework.ts.model.OrderBrief;
import com.homework.ts.model.User;
import com.homework.ts.ui.activity.AddressDetailActivity;
import com.homework.ts.ui.activity.AddressListActivity;
import com.homework.ts.ui.activity.OrderDetailActivity;
import com.homework.ts.util.Constant;
import com.homework.ts.view.CircleImageView;
import com.homework.ts.view.MyListView;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ts on 2017/3/25.
 */

public class OrderFragment extends Fragment{
    private String TAG = "OrderFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mListView;

    public static OrderListAdapter adapter;

    private int lastItemIndex;
    private int dataCount, count;
    private int load_num = 100;

    private boolean isRefresh = false;


    private ProgressWheel progressWheel;
    private TextView textview_tipc;

    public static ArrayList<OrderBrief> ordersList = new ArrayList<>();

    public static OrderFragment newInstance(int sectionNumber) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        initView(rootView);

        return rootView;
    }

    public void initView(View rootView){
        Log.i(TAG,"initView");

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        textview_tipc = (TextView) rootView.findViewById(R.id.textview_tipc);
        mListView = (ListView) rootView.findViewById(R.id.listview_orders);


        if(getActivity() != null){
            getOrders();
        }

        adapter = new OrderListAdapter(getActivity(), ordersList);
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"click"+position);
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);


//                bundle.putParcelableArrayList("list", Constant.allOrdersList);

                Bundle bundle = new Bundle();
                bundle.putParcelable("obj", ordersList.get(position));
//                bundle.putParcelable("obj", Constant.allOrdersList.get(position));
//                bundle.putString("name", allAddressesList.get(position).getName());
//                bundle.putString("city", allAddressesList.get(position).getCity());
//                bundle.putString("district", allAddressesList.get(position).getDistrict());
//                bundle.putString("phone", allAddressesList.get(position).getPhone());
//                bundle.putString("address1", allAddressesList.get(position).getAddress1());
//                bundle.putString("address2", allAddressesList.get(position).getAddress2());
//                bundle.putInt("sex",allAddressesList.get(position).getSex());
//                bundle.putInt("fromwhere",1);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
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
                        Constant.allOrdersList = new ArrayList<>();
                        ordersList.clear();
                        isRefresh = true;
                        getOrders();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        dataCount = load_num;
    }

    public void getOrders(){

        String url = Constant.MY_UTL + "orders/get_user_orders.json?user_id=" + User.getInstance().getId();

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
//                                Constant.allOrdersList = new ArrayList<>();
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("orders");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    OrderBrief order = gson.fromJson(jsonArray.getString(i), OrderBrief.class);

                                    ordersList.add(order);
                                    Constant.allOrdersList.add(order);
                                }

                                textview_tipc.setVisibility(View.GONE);
                                progressWheel.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);

                                adapter = new OrderListAdapter(getActivity(), ordersList);
                                mListView.setAdapter(adapter);
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
