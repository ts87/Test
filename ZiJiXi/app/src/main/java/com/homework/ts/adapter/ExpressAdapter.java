package com.homework.ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Express;
import com.homework.ts.util.DateUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;
import com.homework.ts.zijixi.R;

/**
 * Created by ts on 2017/5/7.
 */

public class ExpressAdapter extends BaseAdapter {

    private List<Express> expressList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public ExpressAdapter(Context context, List<Express> expressList) {
        this.context = context;
        this.expressList = expressList;
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
        return expressList.size();
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
        ExpressAdapter.ViewHolder holder = new ExpressAdapter.ViewHolder();
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_express, parent, false);
            holder = new ExpressAdapter.ViewHolder();
            holder.mTextViewInfo = (TextView) view.findViewById(R.id.text_express_info);
            holder.mTextViewTime = (TextView) view.findViewById(R.id.text_express_time);

            view.setTag(holder);
        } else {
            holder = (ExpressAdapter.ViewHolder) view.getTag();
        }

        if (expressList.size() == 0) {
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        } else {
            holder.mTextViewInfo.setText(expressList.get(position).getSender_type());
            holder.mTextViewTime.setText(DateUtil.DateFormat(expressList.get(position).getExp_time()));
        }


        return view;
    }

    class ViewHolder {
        TextView mTextViewInfo, mTextViewTime;
    }
}
