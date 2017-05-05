package com.homework.ts.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.homework.ts.model.Address;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import java.util.ArrayList;

/**
 * Created by ts on 2017/4/22.
 */

public class OrderDetailActivity extends BaseActivity {
    private String TAG = "OrderDetailActivity";
    private LinearLayout container;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("订单详情");
//        initMenu(toolbar);

        initView();

    }

    protected void initView(){

    }
}
