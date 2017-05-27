package com.homework.ts.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.homework.ts.adapter.AddressAdapter;
import com.homework.ts.adapter.BalanceAdapter;
import com.homework.ts.adapter.YouhuiquanAdapter;
import com.homework.ts.model.User;
import com.homework.ts.model.Wallet;
import com.homework.ts.model.Youhuiquan;
import com.homework.ts.util.Constant;
import com.homework.ts.view.ProgressWheel;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.MainActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ts on 2017/5/12.
 */

public class BalanceActivity extends BaseActivity {
    private String TAG = "BalanceActivity";
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayout container;

    private AlertDialog mDialog = null;

    private int load_num = 100;
    private int fromwhere;

    private Intent intent;

    private ListView listview_balance;
    private ProgressWheel progressWheel;
    private TextView textview_balance;
    private Button chargeBtn;

    private ArrayList<Wallet> walletList = new ArrayList<>();
    private BalanceAdapter adapter;
    private int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        initToolBar("余额");

        intent = getIntent();
        walletList = intent.getParcelableArrayListExtra("list");
        money = intent.getIntExtra("money",0);
        Constant.balance = money;

        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        textview_balance = (TextView) findViewById(R.id.textview_balance);
        chargeBtn = (Button) findViewById(R.id.button_recharge);
        listview_balance = (ListView) findViewById(R.id.listview_balance);

        adapter = new BalanceAdapter(BalanceActivity.this, walletList);
        listview_balance.setAdapter(adapter);

//        fromwhere = getIntent().getIntExtra("fromwhere",0);

        textview_balance.setText(String.valueOf(money));
        chargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog(BalanceActivity.this);
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


    private void showAddDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View textEntryView = inflater.inflate(
                R.layout.dialoglayout, null);
        final EditText edtInput=(EditText)textEntryView.findViewById(R.id.edtInput);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
//        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("请输入充值金额");
        builder.setView(textEntryView);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        charge(edtInput.getText().toString());
//                        showToast("充值成功");
//                        double money = Double.parseDouble(textview_balance.getText().toString())+Double.parseDouble(edtInput.getText().toString());
//                        textview_balance.setText(String.valueOf(money));
//                        Constant.balance = money;
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        builder.show();
    }


    private void charge(final String money){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String httpurl = Constant.MY_UTL + "users/direct_charge?caller=0";

        Map<String, String> params = new HashMap<String, String>();
        params.put("real_money",money);
        params.put("fake_money", "0");
        params.put("user_id", String.valueOf(User.getInstance().getId()));
//        params.put("user_id", "2");
        JSONObject jsonObject = new JSONObject(params);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, httpurl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response -> " + response.toString());

                        String result = null;
                        try {
                            result = response.getString("status");
                            Log.i("Result", response.toString() + "//" );

                            if (result.equals("200")) {
                                showToast("充值成功");
                                int final_money = Integer.parseInt(textview_balance.getText().toString())+Integer.parseInt(money);
                                textview_balance.setText(String.valueOf(final_money));
                                Constant.balance = final_money;

                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("logs");

                                Log.i(TAG,"jsonArray.length()=="+jsonArray.length());

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Wallet wallet = gson.fromJson(jsonArray.getString(i), Wallet.class);
                                    Constant.allWalletList.add(wallet);
                                    walletList.add(wallet);
                                }
//                                adapter.notifyDataSetChanged();
                                adapter = new BalanceAdapter(BalanceActivity.this, walletList);
                                listview_balance.setAdapter(adapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }
}
