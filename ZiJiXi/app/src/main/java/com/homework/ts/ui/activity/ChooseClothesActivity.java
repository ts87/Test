package com.homework.ts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.homework.ts.adapter.ClothesGridAdapter;
import com.homework.ts.model.Address;
import com.homework.ts.model.Clothes;
import com.homework.ts.util.Constant;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ts on 2017/4/14.
 */

public class ChooseClothesActivity extends BaseActivity {
    private String TAG = "ChooseClothesActivity";
    private LinearLayout container;
    private Toolbar toolbar;
    private TextView tvEstimatedPrice;
    private Button button;
    private int categoryID;
    private int estimatedPrice = 0;

    private GridView gv;
    String[] product;

    private ArrayList<Clothes> clothesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clothes);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("专业清洗");

        categoryID = getIntent().getIntExtra("categoryID",1);
        getProducts(categoryID);

        initView();

    }

    public void initView(){
        tvEstimatedPrice = (TextView) this.findViewById(R.id.clothes_estimated_price);
        button = (Button) findViewById(R.id.choose_clothes_button);

        gv = (GridView) this.findViewById(R.id.gridView1);
        final ClothesGridAdapter adapter = new ClothesGridAdapter(this,clothesList);
        gv.setAdapter(adapter);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
//                showToast("arg2="+arg2+"  arg3="+arg3);
                clothesList.get(arg2).setNumber(clothesList.get(arg2).getNumber()+1);
                adapter.notifyDataSetChanged();
                estimatedPrice += clothesList.get(arg2).getPrice1();
                tvEstimatedPrice.setText(String.valueOf(estimatedPrice));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estimatedPrice == 0){
                    showToast("至少要选择一件衣服洗呀～");
                }else{
                    Intent intent = new Intent(ChooseClothesActivity.this, AppointActivity.class);

                    product = new String[clothesList.size()];
                    int j=0;
                    for(int i=0; i<clothesList.size(); i++){
                        if(clothesList.get(i).getNumber() != 0){
                            product[j] = String.valueOf(clothesList.get(i).getId()) + "#"
                                    + String.valueOf(clothesList.get(i).getNumber()) + "#"
                                    + String.valueOf(clothesList.get(i).getPrice1());
                            j++;
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("product",product);
                    bundle.putInt("categoryID",categoryID);
                    intent.putExtras(bundle);
//                    startActivity(intent);
                    startActivityForResult(intent, 0);
                }
            }
        });


    }

    public void getProducts(int categoryID){
        String url = Constant.MY_UTL + "products/get_products.json?category_id=" + String.valueOf(categoryID);

        Log.i("TAG", url);

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        int result = 0;
                        try {
                            result = response.getInt("status");

                            Log.i(TAG,  "/" + response.toString());

                            if (result == 200) {
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("product");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    Log.i(TAG,"jo=="+jo.toString());

                                    Clothes clothes = gson.fromJson(jsonArray.getString(i), Clothes.class);

                                    clothesList.add(clothes);
                                }
                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Json e", e.getMessage());
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"ERROR");
            }
        });
        Log.i(TAG,"jsonObjRequest==/"+jsonObjRequest.toString());
        Constant.queue.add(jsonObjRequest);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String str=b.getString("str1");//str即为回传的值
                Log.i(TAG,"传回"+str);
                finish();
                break;
            default:
                break;
        }
    }
}
