package com.homework.ts.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.homework.ts.adapter.ClothesGridAdapter;
import com.homework.ts.model.Address;
import com.homework.ts.model.Clothes;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import java.util.ArrayList;

/**
 * Created by ts on 2017/4/14.
 */

public class ChooseClothesActivity extends BaseActivity {
    private String TAG = "ChooseClothesActivity";
    private LinearLayout container;
    private Toolbar toolbar;

    private GridView gv;

    private ArrayList<Clothes> clothesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clothes);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("专业清洗");

        for(int i=0; i<10; i++){
            Clothes c = new Clothes("小裙纸","¥25.0","");
            clothesList.add(c);
        }


        initView();

    }

    public void initView(){
        gv = (GridView) this.findViewById(R.id.gridView1);
        ClothesGridAdapter adapter = new ClothesGridAdapter(this,clothesList);
        gv.setAdapter(adapter);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                showToast("arg2="+arg2+"  arg3="+arg3);
            }
        });
    }
}
