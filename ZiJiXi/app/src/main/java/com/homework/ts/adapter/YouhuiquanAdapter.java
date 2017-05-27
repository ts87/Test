package com.homework.ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Youhuiquan;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by ts on 2017/5/6.
 */

public class YouhuiquanAdapter extends BaseAdapter {

    private List<Youhuiquan> youhuiquanList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public YouhuiquanAdapter(Context context,List<Youhuiquan> youhuiquanList){
        this.context = context;
        this.youhuiquanList = youhuiquanList;
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
        return youhuiquanList.size();
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
        YouhuiquanAdapter.ViewHolder holder = new YouhuiquanAdapter.ViewHolder();
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_youhuiquan, parent, false);
            holder = new YouhuiquanAdapter.ViewHolder();
            holder.mTextViewName = (TextView) view.findViewById(R.id.textView_name);
            holder.mTextViewPrice = (TextView) view.findViewById(R.id.textview_price);
            holder.mTextViewRule = (TextView)view.findViewById(R.id.textView_rule);
            holder.mTextDate = (TextView)view.findViewById(R.id.textView_date);

            view.setTag(holder);
        } else {
            holder = (YouhuiquanAdapter.ViewHolder) view.getTag();
        }

        if(youhuiquanList.size()==0){
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        }else{
            holder.mTextViewName.setText(youhuiquanList.get(position).getCoupon_origin().getName());
            holder.mTextViewPrice.setText(String.valueOf(youhuiquanList.get(position).getCoupon_orders().getDiscount()));
            holder.mTextViewRule.setText("满"+String.valueOf(youhuiquanList.get(position).getCoupon_orders().getPremise())+"元可用");
            holder.mTextDate.setText("有效期：" +youhuiquanList.get(position).getCoupon_origin().getValid_from() + "-" +youhuiquanList.get(position).getCoupon_origin().getValid_to());

        }



        return view;
    }

    class ViewHolder{
        TextView mTextViewName,mTextViewPrice,mTextViewRule,mTextDate;
    }
}
