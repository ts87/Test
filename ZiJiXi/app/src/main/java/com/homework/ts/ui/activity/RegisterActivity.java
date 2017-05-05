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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.homework.ts.model.User;
import com.homework.ts.ui.fragment.MyFragment;
import com.homework.ts.util.Constant;
import com.homework.ts.util.MyCounts;
import com.homework.ts.util.UtilMethod;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.MainActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
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
                    String password = UtilMethod.parseStrToMd5L32(mEdittextPassword.getText().toString());

                if(mEidtTextMobile.getText().toString() == null || mEidtTextMobile.getText().toString().equals("")){
                    showToast("请输入手机号");
                }else if(mEdittextPassword.getText().toString() == null || mEdittextPassword.getText().toString().equals("")) {
                    showToast("请输入密码");
                }else if(mEditTextSmsCode.getText().toString() == null || mEditTextSmsCode.getText().toString().equals("")) {
                    showToast("请输入验证码");
                }else{
                    testRegister(mEidtTextMobile.getText().toString(), mEdittextPassword.getText().toString());

                }

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



    private void testRegister(final String mobileNum, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String httpurl = Constant.MY_UTL + "users/register";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobileNum);
        params.put("email",mobileNum+"@qq.com");
        params.put("encrypted_password",password);
        params.put("name", "ts");
        JSONObject jsonObject = new JSONObject(params);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,httpurl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response -> " + response.toString());

                        String result = null;
                        try {
                            result = response.getString("status");
                            Log.i("Result", response.toString() + "//" + password);


                            if (result.equals("200")) {
                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                                login(mobileNum, password);
                            }

                            if (result.equals("555")) {
                                String message = response.getString("message");
                                if (message.equals("exist")){
                                    Toast.makeText(getApplicationContext(), "用户已存在", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }

    private void saveUserInfo(User user) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(user).toString();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("json", jsonObject);
        editor.commit();

    }


    private void login(final String mobileNum, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String httpurl = Constant.MY_UTL + "users/login";

        Log.i(TAG,"mobile="+mobileNum+"  password="+password);
        Map<String, String> params = new HashMap<String, String>();
        params.put("password",password);
        params.put("mobile", mobileNum);
        params.put("email",mobileNum+"@qq.com");
        params.put("name", "ts");
        JSONObject jsonObject = new JSONObject(params);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, httpurl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "response -> " + response.toString());

                        String result = null;
                        try {
                            result = response.getString("status");
                            Log.i("Result", response.toString() + "//" + password);


                            if (result.equals("200")) {
                                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

                                JSONObject resultUser = response.getJSONObject("data");
                                Log.i("Result", resultUser + "");
                                Gson gson = new Gson();
                                User user = gson.fromJson(resultUser.toString(), User.class);
                                User.newInstance(user);
                                User.getInstance().setIsLogin(true);
                                Constant.isLogin = true;
                                saveUserInfo(user);

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                finish();
                            }

                            if (result.equals("555")) {
                                String message = response.getString("message");
                                if (message.equals("wrong")){
                                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }
        };
        requestQueue.add(jsonRequest);
    }

}
