package com.homework.ts.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homework.ts.util.MyCounts;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.MainActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ts on 2017/3/25.
 */

public class RegisterActivity extends BaseActivity {
    private String TAG = "RegisterActivity";
    private EditText mEidtTextMobile, mEdittextPassword, mEditTextSmsCode;
    private Button mButton;
    private TextView mSendVerCode;

    private MyCounts myCount;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);

        toolbar = initToolBar("注册");
        initView();
        myCount = new MyCounts(getApplicationContext(), 60000, 1000, mSendVerCode);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        mEdittextPassword = (EditText) findViewById(R.id.edit_password);
        mEidtTextMobile = (EditText) findViewById(R.id.edit_mobile_num);
        mEditTextSmsCode = (EditText) findViewById(R.id.edit_code);
        mButton = (Button) findViewById(R.id.button_register);
        mSendVerCode = (TextView) findViewById(R.id.textView_ver_code);

        mSendVerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCount.start();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 实现点击空白处，软键盘消失事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

}
