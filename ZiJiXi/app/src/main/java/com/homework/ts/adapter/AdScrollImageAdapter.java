package com.homework.ts.adapter;

/**
 * Created by ts on 2017/3/25.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;


public class AdScrollImageAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<String> imgUrlList;

    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public AdScrollImageAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.imgUrlList = list;

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


    public int getCount() {
//		    return imgUrlList.size();
        return Integer.MAX_VALUE;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            ImageView imageView = new ImageView(context);
//			    imageView.setAdjustViewBounds(true);

            imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView = imageView;
            viewHolder.imageView = (ImageView)convertView;
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(imgUrlList.size()==0){//没有广告可以显示
//            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ali));
        }else{
            ImageLoader.getInstance().displayImage(imgUrlList.get(position % imgUrlList.size()), viewHolder.imageView, options, animateFirstListener);
        }


        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
    }

}

