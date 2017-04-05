package com.homework.ts.zijixi;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.homework.ts.ui.fragment.HomeFragment;
import com.homework.ts.ui.fragment.MyFragment;
import com.homework.ts.ui.fragment.OrderFragment;

import java.util.Locale;

import me.drakeet.materialdialog.MaterialDialog;
import com.homework.ts.util.Constant;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    private MaterialDialog mMaterialDialog = null;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private LinearLayout container;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton0;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constant.finishActivities.add(this);
        container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);//设置状态栏
        //检查网络
        checkNetWork();
        //获取使用手机权限
//        getPersimmions();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(onPageChangeListener);

        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mRadioButton0 = (RadioButton) findViewById(R.id.radioButton0);
        mRadioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        mRadioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }




    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0://首页
                    mRadioButton0.setChecked(true);
                    mRadioButton1.setChecked(false);
                    mRadioButton2.setChecked(false);
                    mRadioButton0.setTextColor(getResources().getColor(R.color.light_blue));
                    mRadioButton1.setTextColor(getResources().getColor(R.color.text_gray));
                    mRadioButton2.setTextColor(getResources().getColor(R.color.text_gray));
                    break;
                case 1://订单
                    mRadioButton0.setChecked(false);
                    mRadioButton1.setChecked(true);
                    mRadioButton2.setChecked(false);
                    mRadioButton0.setTextColor(getResources().getColor(R.color.text_gray));
                    mRadioButton1.setTextColor(getResources().getColor(R.color.light_blue));
                    mRadioButton2.setTextColor(getResources().getColor(R.color.text_gray));
                    break;
                case 2://我的
                    mRadioButton0.setChecked(false);
                    mRadioButton1.setChecked(false);
                    mRadioButton2.setChecked(true);
                    mRadioButton0.setTextColor(getResources().getColor(R.color.text_gray));
                    mRadioButton2.setTextColor(getResources().getColor(R.color.light_blue));
                    mRadioButton1.setTextColor(getResources().getColor(R.color.text_gray));
                    break;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Code goes here
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Code goes here
        }
    };

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radioButton0://首页
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.radioButton1://订单
                    mViewPager.setCurrentItem(1);
                    break;

                case R.id.radioButton2://我的
                    mViewPager.setCurrentItem(2);
                    break;

            }
        }
    };

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(android.app.FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance(0);
                case 1:
                    return OrderFragment.newInstance(1);
                case 2:
                    return MyFragment.newInstance(2);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 1:
                    return getString(R.string.title_section0).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }


    /**
     * 拦截返回键，按两次推出程序
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出程序的方法
     */
    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出！", Toast.LENGTH_SHORT)
                    .show();
            exitTime = System.currentTimeMillis();
        } else {
            ruin();
            finish();
            MainActivity.this.finish();
        }
    }

    private void ruin() {
        try {
            for (Activity activity : Constant.finishActivities) {
                if (activity != null)
                    activity.finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onResume() {
        super.onResume();
//        if (User.getInstance().isLogin()) {
//            GetReceiveMsgPersenter persenter = new GetRecevieMsgPresenterImpl(this);
//            persenter.getReceiveMsg(User.getInstance().getUser_id(),1);
//        } else {
//            Resources res = MainActivity.this.getResources();
//            Drawable myImage2 = res.getDrawable(R.drawable.tab_button_topic_selected);
//            mRadioButton2.setCompoundDrawablesWithIntrinsicBounds(null, myImage2, null, null);
//        }
    }

    public void onPause() {
        super.onPause();
    }


    private void checkNetWork(){
        if (!isNetworkConnected(getApplicationContext())) {
            mMaterialDialog = new MaterialDialog(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("无网络服务")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            System.exit(0);
                            mMaterialDialog.dismiss();
                        }
                    })
                    .setNegativeButton("设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                            if (isNetworkConnected(getApplicationContext())) {

                            }
                        }
                    });
            this.mMaterialDialog.show();
        }
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
