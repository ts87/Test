package com.homework.ts.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.homework.ts.adapter.AddressAdapter;
import com.homework.ts.adapter.OrderListAdapter;
import com.homework.ts.model.Address;
import com.homework.ts.model.OrderBrief;
import com.homework.ts.ui.activity.AddressDetailActivity;
import com.homework.ts.ui.activity.AddressListActivity;
import com.homework.ts.view.CircleImageView;
import com.homework.ts.view.MyListView;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.R;

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

    private OrderListAdapter adapter;

    private int lastItemIndex;
    private int dataCount, count;
    private int load_num = 100;

    private boolean isRefresh = false;


    private ProgressWheel progressWheel;
    private TextView textview_tipc;

    private ArrayList<OrderBrief> ordersList = new ArrayList<>();

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

        progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        textview_tipc = (TextView) rootView.findViewById(R.id.textview_tipc);
        mListView = (ListView) rootView.findViewById(R.id.listview_orders);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), AddressDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("name", allAddressesList.get(position).getName());
//                bundle.putString("city", allAddressesList.get(position).getCity());
//                bundle.putString("district", allAddressesList.get(position).getDistrict());
//                bundle.putString("phone", allAddressesList.get(position).getPhone());
//                bundle.putString("address1", allAddressesList.get(position).getAddress1());
//                bundle.putString("address2", allAddressesList.get(position).getAddress2());
//                bundle.putInt("sex",allAddressesList.get(position).getSex());
//                bundle.putInt("fromwhere",1);
//                intent.putExtras(bundle);
//                startActivity(intent);

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
                        ordersList = new ArrayList<>();
                        isRefresh = true;
//                        getActivities(count, 1);//!!!!!!!!!!!!!!!!!
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        dataCount = load_num;

        getOrders();


        adapter = new OrderListAdapter(getActivity(), ordersList);
        mListView.setAdapter(adapter);
    }

    public void getOrders(){
        OrderBrief o1 = new OrderBrief("12345678909876","2017-03-29 14:00-15:00","取件中","T-shirt","支付");
        OrderBrief o2 = new OrderBrief("98765094828742","2017-04-01 10:00-11:00","已签收","衬衫","评价");
        ordersList.add(o1);
        ordersList.add(o2);

        progressWheel.setVisibility(View.GONE);
        textview_tipc.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }
}
