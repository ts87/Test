package com.homework.ts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Address;
import com.homework.ts.model.Clothes;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if(clothesList.size()==0){
//            holder.mTextViewName.setText("none");
//            holder.mTextViewPhone.setText("none");
//            holder.mTextViewDetail.setText("none");
        }else{
            holder.mTextViewName.setText(clothesList.get(position).getClothesName());
            holder.mTextViewPrice.setText(clothesList.get(position).getClothesPrice());
            holder.mTextViewImage.setImageDrawable(context.getResources().getDrawable(R.drawable.clothes));

//            ImageLoader.getInstance().displayImage(clothesList.get(position).getClothesImage(), holder.mTextViewImage, options, animateFirstListener);

        }



        return view;
    }

    class ViewHolder{
        TextView mTextViewName,mTextViewPrice;
        ImageView mTextViewImage;
    }
}