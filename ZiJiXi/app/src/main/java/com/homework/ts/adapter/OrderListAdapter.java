package com.homework.ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Address;
import com.homework.ts.model.OrderBrief;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by ts on 2017/4/14.
 */

public class OrderListAdapter extends BaseAdapter {

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
            holder.mTextViewNumber.setText("订单编号：" + ordersList.get(position).getNumber());
            holder.mTextViewTime.setText("取件时间：" + ordersList.get(position).getTime());
            holder.mTextViewType.setText(ordersList.get(position).getType());
            holder.mTextViewState.setText(ordersList.get(position).getState());
            holder.button.setText(ordersList.get(position).getButtonText());
        }



        return view;
    }

    class ViewHolder{
        TextView mTextViewNumber,mTextViewState,mTextViewType,mTextViewTime;
        Button button;
    }

}
