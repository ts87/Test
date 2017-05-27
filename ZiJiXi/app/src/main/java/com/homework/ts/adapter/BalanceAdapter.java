package com.homework.ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Address;
import com.homework.ts.model.Wallet;
import com.homework.ts.util.DateUtil;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by ts on 2017/5/13.
 */

public class BalanceAdapter extends BaseAdapter {
    private List<Wallet> walletList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public BalanceAdapter(Context context,List<Wallet> walletList){
        this.context = context;
        this.walletList = walletList;
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
        return walletList.size();
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
            view = inflater.inflate(R.layout.item_balance, parent, false);
            holder = new ViewHolder();
            holder.mTextViewMoney = (TextView) view.findViewById(R.id.textView_money);
            holder.mTextViewType = (TextView) view.findViewById(R.id.textView_type);
            holder.mTextViewTime = (TextView)view.findViewById(R.id.textview_time);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(walletList.size()==0){
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        }else{
//            holder.mTextViewMoney.setText("充值金额：¥"+walletList.get(position).getReal_money());
            holder.mTextViewMoney.setText("金额：¥"+ (walletList.get(position).getReal_money()+walletList.get(position).getFake_money()));
            holder.mTextViewType.setText(""+walletList.get(position).getLoggable_type());
            holder.mTextViewTime.setText("时间"+ DateUtil.DateFormat(walletList.get(position).getCreated_at()));

        }

        return view;
    }

    class ViewHolder{
        TextView mTextViewMoney,mTextViewType,mTextViewTime;
    }
}
