package com.homework.ts.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.homework.ts.adapter.AdScrollImageAdapter;
import com.homework.ts.ui.activity.ChooseClothesActivity;
import com.homework.ts.view.MyGallery;
import com.homework.ts.view.MyListView;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.R;
import com.homework.ts.util.Constant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ts on 2017/3/25.
 */

public class HomeFragment extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ProgressWheel progressWheel;
    private String TAG = "HomeFragment";
    private ScrollView scrollView;
    private View rootView;
    private MyGallery gallery = null;
//    private ArrayList<Activities> bannerList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout relative_clothes, relative_shoes, relative_chuanglian, relative_jiafang;

    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        args.putInt("num", sectionNumber);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);

            scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

            gallery = (MyGallery) rootView.findViewById(R.id.banner_gallery);

            relative_clothes = (RelativeLayout) rootView.findViewById(R.id.fragment_home_relative_clothes);
            relative_shoes = (RelativeLayout) rootView.findViewById(R.id.fragment_home_relative_shoes);
            relative_chuanglian = (RelativeLayout) rootView.findViewById(R.id.fragment_home_relative_chuanglian);
            relative_jiafang = (RelativeLayout) rootView.findViewById(R.id.fragment_home_relative_jiafang);

            scrollView.smoothScrollTo(0, 20);

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
//                            getFindData();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 3000);
                }
            });


            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {//点击活动广告

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                    int position = arg2 % bannerList.size();

//                    Intent intent2 = new Intent(getActivity(), WebViewActivity.class);
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putString("value", bannerList.get(position).getOut_enroll_url());
//                    bundle2.putString("flag", "活动详情");
//                    intent2.putExtras(bundle2);
//                    startActivity(intent2);

                }
            });


            gallery.setAdapter(new AdScrollImageAdapter(getActivity(), Constant.adURL));


            relative_clothes.setOnClickListener(new View.OnClickListener() {//衣服！！！
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(getActivity(), ChooseClothesActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("categoryID",1);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                }
            });

            relative_shoes.setOnClickListener(new View.OnClickListener() {//鞋！！！
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(getActivity(), ChooseClothesActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("categoryID",2);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                }
            });

            relative_chuanglian.setOnClickListener(new View.OnClickListener() {//窗帘！！！
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(getActivity(), ChooseClothesActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("categoryID",3);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                }
            });

            relative_jiafang.setOnClickListener(new View.OnClickListener() {//家纺！！！
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(getActivity(), ChooseClothesActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("categoryID",4);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                }
            });

        }
        return rootView;
    }
}
