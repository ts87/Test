package com.homework.ts.adapter;

/**
 * Created by ts on 2017/3/25.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Address;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Calendar;
import java.util.List;

public class AddressAdapter extends BaseAdapter {

    private List<Address> addressesList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public AddressAdapter(Context context,List<Address> addressesList){
        this.context = context;
        this.addressesList = addressesList;
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
        return addressesList.size();
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
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_address, parent, false);
            holder = new ViewHolder();
            holder.mTextViewName = (TextView) view.findViewById(R.id.textView_name);
            holder.mTextViewPhone = (TextView) view.findViewById(R.id.textView_phone);
            holder.mTextViewDetail = (TextView)view.findViewById(R.id.textview_detail);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(addressesList.size()==0){
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        }else{
            holder.mTextViewName.setText(addressesList.get(position).getName());
            holder.mTextViewPhone.setText(addressesList.get(position).getTel());
            holder.mTextViewDetail.setText(addressesList.get(position).getCity() + addressesList.get(position).getRegion() +
                    addressesList.get(position).getCommunity()+" "+addressesList.get(position).getHouse_number());

        }



        return view;
    }

    class ViewHolder{
        TextView mTextViewName,mTextViewPhone,mTextViewDetail;
    }
}