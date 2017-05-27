package com.homework.ts.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Address;
import com.homework.ts.model.OrderBrief;
import com.homework.ts.model.User;
import com.homework.ts.model.Youhuiquan;
import com.homework.ts.ui.activity.AppointActivity;
import com.homework.ts.ui.activity.ChooseClothesActivity;
import com.homework.ts.ui.activity.OrderPayActivity;
import com.homework.ts.ui.activity.YouhuiquanActivity;
import com.homework.ts.ui.fragment.OrderFragment;
import com.homework.ts.util.Constant;
import com.homework.ts.util.DateUtil;
import com.homework.ts.zijixi.MainActivity;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ts on 2017/4/14.
 */

public class OrderListAdapter extends BaseAdapter {

    private String TAG = "OrderListAdapter";
    private List<OrderBrief> ordersList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public OrderListAdapter(Context context,List<OrderBrief> ordersList){
        this.context = context;
        this.ordersList = ordersList;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.placeholder)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                //.displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        OrderListAdapter.ViewHolder holder = new OrderListAdapter.ViewHolder();
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_order, parent, false);
            holder = new OrderListAdapter.ViewHolder();
            holder.mTextViewNumber = (TextView) view.findViewById(R.id.textview_order_number);
            holder.mTextViewTime = (TextView) view.findViewById(R.id.textview_order_time);
            holder.mTextViewType = (TextView)view.findViewById(R.id.textView_type);
            holder.mTextViewState = (TextView)view.findViewById(R.id.textView_state);
            holder.button = (Button)view.findViewById(R.id.order_button);
            view.setTag(holder);
        } else {
            holder = (OrderListAdapter.ViewHolder) view.getTag();
        }

        if(ordersList.size()==0){
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        }else{
            holder.mTextViewNumber.setText("订单编号：" + ordersList.get(position).getId());
            holder.mTextViewTime.setText("取件时间：" + DateUtil.DateFormat(ordersList.get(position).getCreated_at()));
            holder.mTextViewType.setText(ordersList.get(position).getCategory_name());
            if(ordersList.get(position).getStatus() == 1) {
                holder.mTextViewState.setText("派单中");
                holder.button.setVisibility(View.VISIBLE);
                holder.button.setText("取消");
            }else if(ordersList.get(position).getStatus() == 2) {
                holder.mTextViewState.setText("取件中");
                holder.button.setVisibility(View.VISIBLE);
                holder.button.setText("取消");
            }else if(ordersList.get(position).getStatus() == 3){
                holder.mTextViewState.setText("待支付");
                holder.button.setVisibility(View.VISIBLE);
                holder.button.setText("支付");
            }else if(ordersList.get(position).getStatus() == 4){
                holder.mTextViewState.setText("送往加工店");
                holder.button.setVisibility(View.VISIBLE);
                holder.button.setText("取消");
            }else if(ordersList.get(position).getStatus() == 5){
                holder.mTextViewState.setText("清洗中");
                holder.button.setVisibility(View.INVISIBLE);
            }else if(ordersList.get(position).getStatus() == 6){
                holder.mTextViewState.setText("送回中");
                holder.button.setVisibility(View.INVISIBLE);
            }else if(ordersList.get(position).getStatus() == 7){
                holder.mTextViewState.setText("已签收");
                holder.button.setVisibility(View.INVISIBLE);
                holder.button.setText("申请退款");
            }else if(ordersList.get(position).getStatus() == 9) {
                holder.mTextViewState.setText("申请退款中");
                holder.button.setVisibility(View.INVISIBLE);
            }else if(ordersList.get(position).getStatus() == 10) {
                holder.mTextViewState.setText("订单已取消");
                holder.button.setVisibility(View.INVISIBLE);
            }else if(ordersList.get(position).getStatus() == 11) {
                holder.mTextViewState.setText("订单已退款");
                holder.button.setVisibility(View.INVISIBLE);
            }
//            holder.mTextViewState.setText(ordersList.get(position).getStatus());
//            holder.button.setText(ordersList.get(position).getButtonText());
        }

        final int id = position;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"click button~~~");
                if(ordersList.get(id).getStatus() == 3){//待支付
                    Intent intent = new Intent(context, OrderPayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("obj", ordersList.get(id));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else if(ordersList.get(id).getStatus() == 1 || ordersList.get(id).getStatus() == 2 || ordersList.get(id).getStatus() == 4){//派单中、取件中
                    applyCancelOrder(ordersList.get(id).getId());
                }
            }
        });
        return view;
    }

    class ViewHolder{
        TextView mTextViewNumber,mTextViewState,mTextViewType,mTextViewTime;
        Button button;
    }

  /*  private void cancelOrder(final int order_id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String httpurl = Constant.MY_UTL + "users/cancel_order";

        Map<String, String> params = new HashMap<>();
        params.put("order_id",String.valueOf(order_id));
        params.put("user_id", String.valueOf(User.getInstance().getId()));

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
                                String message = response.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                                for(int i = 0; i<OrderFragment.ordersList.size(); i++){
                                    if(OrderFragment.ordersList.get(i).getId() == order_id){
                                        OrderFragment.ordersList.get(i).setStatus(10);//订单取消
                                    }
                                }
                                OrderFragment.adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
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
    }*/

    private void applyCancelOrder(final int order_id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String httpurl = Constant.MY_UTL + "users/request_cancel_order";

        Map<String, String> params = new HashMap<>();
        params.put("order_id",String.valueOf(order_id));
        params.put("user_id", String.valueOf(User.getInstance().getId()));

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
                                String message = response.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                                for(int i = 0; i<OrderFragment.ordersList.size(); i++){
                                    if(OrderFragment.ordersList.get(i).getId() == order_id){
                                        OrderFragment.ordersList.get(i).setStatus(9);//订单申请退款
                                    }
                                }
                                OrderFragment.adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
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
