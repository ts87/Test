package com.homework.ts.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.User;
import com.homework.ts.ui.activity.AddressListActivity;
import com.homework.ts.ui.activity.LoginActivity;
import com.homework.ts.ui.activity.RegisterActivity;
import com.homework.ts.util.PictureUtil;
import com.homework.ts.view.CircleImageView;
import com.homework.ts.view.MyListView;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ts on 2017/3/25.
 */

public class MyFragment extends Fragment {
    private String TAG = "MyFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
//    String filename = Environment.getExternalStorageDirectory().toString()+ File.separator+"oxcoder/image/";
    /*UI*/

    private RelativeLayout linearBackground;
    private CircleImageView imagePortrait;
    public static TextView textPhoneNumber;
    private TextView textMoney;
    private TextView textQuan;
    private RelativeLayout relativeMoney;
    private RelativeLayout relativeQuan;
    private RelativeLayout relativeAddress;
    private Button logoutButton;


    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private LayoutInflater inflater;

    public static MyFragment newInstance(int sectionNumber) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        initView(rootView);


        return rootView;
    }

    protected void initView(View rootView){
        // 头像和毛玻璃背景
        Bitmap bitmap;
        Bitmap blurBitmap = null;

        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ali87);//头像
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.personal_background_default);
        blurBitmap = PictureUtil.fastblur(bitmap2,10);


        linearBackground = (RelativeLayout) rootView.findViewById(R.id.relative_my_background);
        linearBackground.setBackgroundDrawable(new BitmapDrawable(blurBitmap));

        imagePortrait = (CircleImageView) rootView.findViewById(R.id.user_portrait);
        imagePortrait.setImageBitmap(bitmap);

//        inflater = LayoutInflater.from(getActivity());
//        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.portrait_gray)
//                .showImageForEmptyUri(R.drawable.portrait_gray)
//                .showImageOnFail(R.drawable.portrait_gray)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .build();
//        ImageLoader.getInstance().displayImage(Constant.USER_IMAGE_PATH + userPortrait, imagePortrait, options, animateFirstListener);

        textPhoneNumber = (TextView) rootView.findViewById(R.id.textview_my_phonenumber);
        textMoney = (TextView) rootView.findViewById(R.id.fragment_my_text_money);
        textQuan = (TextView) rootView.findViewById(R.id.fragment_my_text_quan);
        relativeMoney = (RelativeLayout) rootView.findViewById(R.id.fragment_my_relative_money);
        relativeQuan = (RelativeLayout) rootView.findViewById(R.id.fragment_my_relative_quan);
        relativeAddress = (RelativeLayout) rootView.findViewById(R.id.fragment_my_relative_address);

        logoutButton = (Button) rootView.findViewById(R.id.button_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        relativeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fromwhere",0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        textPhoneNumber.setText(User.getInstance().getMobile());


    }

}
