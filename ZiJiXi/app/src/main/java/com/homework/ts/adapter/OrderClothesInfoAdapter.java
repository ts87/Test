package com.homework.ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.ClothesInfo;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by ts on 2017/5/6.
 */

public class OrderClothesInfoAdapter extends BaseAdapter {

    private List<ClothesInfo> clothesInfoList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public OrderClothesInfoAdapter(Context context,List<ClothesInfo> clothesInfoList){
        this.context = context;
        this.clothesInfoList = clothesInfoList;
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
        return clothesInfoList.size();
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
        OrderClothesInfoAdapter.ViewHolder holder = new OrderClothesInfoAdapter.ViewHolder();
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_clothes_info, parent, false);
            holder = new OrderClothesInfoAdapter.ViewHolder();
            holder.mTextViewName = (TextView) view.findViewById(R.id.textview_clothes_name);
            holder.mTextViewNumber = (TextView) view.findViewById(R.id.textview_clothes_number);
            holder.mTextViewPrice = (TextView) view.findViewById(R.id.textview_clothes_price);

            view.setTag(holder);
        } else {
            holder = (OrderClothesInfoAdapter.ViewHolder) view.getTag();
        }

        if(clothesInfoList.size()==0){
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        }else{
            holder.mTextViewName.setText(clothesInfoList.get(position).getName());
            holder.mTextViewNumber.setText("x"+String.valueOf(clothesInfoList.get(position).getAmount()));
            holder.mTextViewPrice.setText(String.valueOf(clothesInfoList.get(position).getPrice())+".00元");
        }



        return view;
    }

    class ViewHolder{
        TextView mTextViewName,mTextViewNumber,mTextViewPrice;
    }
}
