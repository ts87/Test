package com.homework.ts.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homework.ts.view.CircleImageView;
import com.homework.ts.view.MyListView;
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
    private TextView mTextView;
    private MyListView mListView;//列表
    private ImageView ivSetting;
    private CircleImageView mImageView;//头像

    /*列表的适配器*/
    private List<HashMap<String, String>> list = null;
    private List<HashMap<String, String>> listData;
    private List<String> groupkey = new ArrayList<String>();

    private String srcPath;

    Map<String, String> params = new HashMap<String, String>();

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
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

//        initView(rootView);

        return rootView;
    }
}
