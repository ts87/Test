package com.homework.ts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.homework.ts.adapter.AddressAdapter;
import com.homework.ts.model.Address;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import java.util.ArrayList;

/**
 * Created by ts on 2017/3/25.
 */

public class AddressDetailActivity extends BaseActivity {
    private String TAG = "AddressDetailActivity";
    private LinearLayout container;
    private EditText editCity, editDistrict, editName, editPhone, editAddress1, editAddress2;
    private RadioButton radioMale, radioFemale;
    private Button deletaButton;
    private Toolbar toolbar;

    private String city, district, name, phone, address1, address2;
    private int sex; // 0-femail, 1-male
    private int fromwhere; // 0-添加, 1-详情


    private ArrayList<Address> allAddressesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("填写地址");
        initMenu(toolbar);

        initView();

    }

    protected void initView(){
        Intent intent = getIntent();

        editCity = (EditText) findViewById(R.id.address_edit_city);
        editDistrict = (EditText) findViewById(R.id.address_edit_district);
        editAddress1 = (EditText) findViewById(R.id.address_edit_address1);
        editAddress2 = (EditText) findViewById(R.id.address_edit_address2);
        editName = (EditText) findViewById(R.id.address_edit_name);
        editPhone = (EditText) findViewById(R.id.address_edit_phone);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        deletaButton = (Button) findViewById(R.id.button_delete_address);

        fromwhere = intent.getIntExtra("fromwhere",-1);
        if(fromwhere == 1){// 详情
            city = intent.getStringExtra("city");
            district = intent.getStringExtra("district");
            address1 = intent.getStringExtra("address1");
            address2 = intent.getStringExtra("address2");
            name = intent.getStringExtra("name");
            phone = intent.getStringExtra("phone");
            sex = intent.getIntExtra("sex",-1);

            editCity.setText(city);
            editDistrict.setText(district);
            editAddress1.setText(address1);
            editAddress2.setText(address2);
            editName.setText(name);
            editPhone.setText(phone);
            if(sex == 0){
                radioFemale.setChecked(true);
                radioMale.setChecked(false);
            }else if(sex == 1){
                radioMale.setChecked(true);
                radioFemale.setChecked(false);
            }else{
                radioFemale.setChecked(false);
                radioMale.setChecked(false);
            }

            deletaButton.setVisibility(View.VISIBLE);
        }else{// 添加
            deletaButton.setVisibility(View.GONE);
        }
    }

    private void initMenu(Toolbar toolbar) {
        TextView subView = new TextView(this);
        subView.setTextColor(getResources().getColor(R.color.white));
        subView.setText("保存");
        subView.setTextSize(18);
        //设定布局的各种参数
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT,
                Gravity.RIGHT);
        params.setMargins(3, 3, 30, 3);//设置外边界
        subView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        subView.setLayoutParams(params);
        toolbar.addView(subView);
    }
}
