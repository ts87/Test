package com.homework.ts.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.homework.ts.adapter.YouhuiquanAdapter;
import com.homework.ts.listener.AnimateFirstDisplayListener;
import com.homework.ts.model.Address;
import com.homework.ts.model.CouponInfo;
import com.homework.ts.model.CouponOrders;
import com.homework.ts.model.CouponOrigin;
import com.homework.ts.model.User;
import com.homework.ts.model.Wallet;
import com.homework.ts.model.Youhuiquan;
import com.homework.ts.ui.activity.AddressListActivity;
import com.homework.ts.ui.activity.BalanceActivity;
import com.homework.ts.ui.activity.LoginActivity;
import com.homework.ts.ui.activity.RegisterActivity;
import com.homework.ts.ui.activity.YouhuiquanActivity;
import com.homework.ts.util.Constant;
import com.homework.ts.util.PictureUtil;
import com.homework.ts.view.CircleImageView;
import com.homework.ts.view.MyListView;
import com.homework.ts.zijixi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    public static TextView textMoney;
    public static TextView textQuan;
    private RelativeLayout relativeMoney;
    private RelativeLayout relativeQuan;
    private RelativeLayout relativeAddress;
    private Button logoutButton;

    private int money;
    protected boolean isCreate = false;

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
        isCreate = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreate) {
            //相当于Fragment的onResume
            //在这里处理加载数据等操作
            getBalance();
            getCouponNumber();
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        getBalance();
        getCouponNumber();

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

        if(User.getInstance().getId() == 0){
            logoutButton.setBackgroundColor(this.getResources().getColor(R.color.light_blue));
            logoutButton.setText("登录");
        }else{
            logoutButton.setBackgroundColor(this.getResources().getColor(R.color.red));
            logoutButton.setText("退出登录");
        }

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

        relativeQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YouhuiquanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fromwhere",0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        relativeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BalanceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", Constant.allWalletList);
                bundle.putInt("money",money);
//                bundle.putInt("fromwhere",0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        textPhoneNumber.setText(User.getInstance().getMobile());


    }

    public void getBalance(){
        String url = Constant.MY_UTL + "users/wallet.json?user_id=" + User.getInstance().getId();
//        String url = Constant.MY_UTL + "users/wallet.json?user_id=2" ;

        Log.i("TAG", url);

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        int result = 0;
                        try {
                            result = response.getInt("status");

                            Log.i(TAG,   "/" + response.toString());

                            if (result == 200) {
                                Constant.allWalletList = new ArrayList<>();
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.getJSONArray("logs");
                                JSONArray jsonWallet = response.getJSONArray("wallet");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Wallet wallet = gson.fromJson(jsonArray.getString(i), Wallet.class);
                                    Constant.allWalletList.add(wallet);
                                }

//                                String money = jsonWallet.getString(0);

                                JSONObject moneyObject = (JSONObject)jsonWallet.get(0);
                                String testMoney = moneyObject.get("money").toString();
                                textMoney.setText(testMoney);

//                                money = (int)testMoney;
                                money = (new Double(testMoney)).intValue();
                                Constant.balance = money;

                                Log.i(TAG,"testMoney=//"+testMoney);


//                                textview_tipc.setVisibility(View.GONE);
//                                progressWheel.setVisibility(View.GONE);
//                                mListView.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Json e", e.getMessage());
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Constant.queue.add(jsonObjRequest);

    }


    public void getCouponNumber() {

        String url = Constant.MY_UTL + "coupons/get_user_coupons.json?user_id=" + User.getInstance().getId();

        Log.i("TAG", url);

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i(TAG, "/" + response.toString());

                            Constant.allYouhuiquanList = new ArrayList<>();
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("coupons");

                            Constant.coupon_number = jsonArray.length();
                            textQuan.setText(String.valueOf(Constant.coupon_number));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Json e", e.getMessage());
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Constant.queue.add(jsonObjRequest);

    }
}
