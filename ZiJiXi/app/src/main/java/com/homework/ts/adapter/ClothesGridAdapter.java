package com.homework.ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Address;
import com.homework.ts.model.Clothes;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ts on 2017/4/14.
 */

public class ClothesGridAdapter extends BaseAdapter {

    private List<Clothes> clothesList;
    private Context context;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public ClothesGridAdapter(Context context,List<Clothes> clothesList){
        this.context = context;
        this.clothesList = clothesList;
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
        return clothesList.size();
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
            view = inflater.inflate(R.layout.item_clothes_grid, parent, false);
            holder = new ViewHolder();
            holder.mTextViewName = (TextView) view.findViewById(R.id.clothes_name);
            holder.mTextViewPrice = (TextView) view.findViewById(R.id.clothes_price);
            holder.mTextViewImage = (ImageView)view.findViewById(R.id.clothes_image);
            holder.mTextViewNumber = (TextView) view.findViewById(R.id.text_clothes_number);
            holder.mLayoutNumber = (LinearLayout) view.findViewById(R.id.linear_number);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(clothesList.size()==0){
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        }else{
            holder.mTextViewName.setText(clothesList.get(position).getName());
            if(clothesList.get(position).getPrice1() == 0){
                holder.mTextViewPrice.setText("¥25.00");
            }else{
                holder.mTextViewPrice.setText("¥"+ clothesList.get(position).getPrice1());
            }
            if(clothesList.get(position).getCategory_id() == 1){//衣服
                holder.mTextViewImage.setImageDrawable(context.getResources().getDrawable(R.drawable.clothes));
            }else if(clothesList.get(position).getCategory_id() == 2) {//鞋
                holder.mTextViewImage.setImageDrawable(context.getResources().getDrawable(R.drawable.shoes));
            }else if(clothesList.get(position).getCategory_id() == 4) {//窗帘
                holder.mTextViewImage.setImageDrawable(context.getResources().getDrawable(R.drawable.chuanglian));
            }else if(clothesList.get(position).getCategory_id() == 3) {//家纺
                holder.mTextViewImage.setImageDrawable(context.getResources().getDrawable(R.drawable.jiafang));
            }

            if(clothesList.get(position).getNumber() == 0){
                holder.mLayoutNumber.setVisibility(View.INVISIBLE);
            }else{
                holder.mLayoutNumber.setVisibility(View.VISIBLE);
                holder.mTextViewNumber.setText(String.valueOf(clothesList.get(position).getNumber()));
            }

//            ImageLoader.getInstance().displayImage(clothesList.get(position).getClothesImage(), holder.mTextViewImage, options, animateFirstListener);

        }

//        holder.mTextViewImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.mTextViewNumber.setVisibility(View.VISIBLE);
//            }
//        });



        return view;
    }

    class ViewHolder{
        TextView mTextViewName,mTextViewPrice,mTextViewNumber;
        ImageView mTextViewImage;
        LinearLayout mLayoutNumber;
    }
}