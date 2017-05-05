package com.homework.ts.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.homework.ts.model.User;
import com.homework.ts.ui.fragment.MyFragment;
import com.homework.ts.util.Constant;
import com.homework.ts.util.UtilMethod;
import com.homework.ts.view.CircleImageView;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ts on 2017/3/25.
 */

public class LoginActivity extends BaseActivity {

    private String TAG = "LoginActivity";
    private EditText editTextPhone, editTextPsw;
    private TextView tvNoLogin, tvPassword;
    private Button mButton;
    private ImageView mImageViewPwd;
    private boolean isShowPwd = false;
    private boolean isPassword = false;
    private Intent intent;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);
        Toolbar toolbar = initToolBar("登录");
        initView();
        initMenu(toolbar);

        intent = getIntent();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button:
                    final String phone = editTextPhone.getText().toString();
                    String password = editTextPsw.getText().toString();

                    if (password.isEmpty() || password.equals("null") || password == null) {
                        isPassword = false;
                    } else {
                        isPassword = true;
                    }

                    password = UtilMethod.parseStrToMd5L32(password);
                    if (phone.isEmpty() || phone.equals("null") || phone == null) {
                        Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!isPassword) {
                        Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (UtilMethod.isMobileNO(phone)) {
                        login(editTextPhone.getText().toString(),editTextPsw.getText().toString());
                        //登录！！！！！！！！！
//                        finish();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入正确的账号", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.imgaeview_pwd:
                    if (isShowPwd) {
                        editTextPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mImageViewPwd.setImageResource(R.mipmap.icon_show_pw);
                    } else {
                        mImageViewPwd.setImageResource(R.mipmap.icon_hide_pw);
                        editTextPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                    isShowPwd = !isShowPwd;
                    //切换后将EditText光标置于末尾
                    CharSequence charSequence = editTextPsw.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                    break;
                case R.id.textView3://忘记密码
                    startActivity(new Intent(getApplicationContext(), LoginForgetPasswordActivity.class));
                    break;

                default:
                    break;
            }
        }
    };



    /**
     * 初始化控件
     */
    public void initView() {
        editTextPhone = (EditText) findViewById(R.id.login_editText_phone);
        editTextPsw = (EditText) findViewById(R.id.login_editText_password);
        tvNoLogin = (TextView) findViewById(R.id.textView3);//忘记密码
        mButton = (Button) findViewById(R.id.login_button);//登录
        mImageViewPwd = (ImageView) findViewById(R.id.imgaeview_pwd);

        mButton.setOnClickListener(onClickListener);
        mImageViewPwd.setOnClickListener(onClickListener);
        tvNoLogin.setOnClickListener(onClickListener);

    }

    /**
     * 设置把保存按钮
     *
     * @param toolbar
     */
    private void initMenu(Toolbar toolbar) {
        TextView subView = new TextView(this);
        subView.setTextColor(getResources().getColor(R.color.white));
        subView.setText("注册");
        subView.setTextSize(18);
        //设定布局的各种参数
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT,
                Gravity.RIGHT);
        params.setMargins(3, 3, 30, 3);//设置外边界
        subView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        subView.setLayoutParams(params);
        toolbar.addView(subView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
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

    @Override
    protected void onRestart() {
        super.onRestart();
//        if (User.getInstance() != null) {
//            if (User.getInstance().isLogin()) {
//                if (User.getInstance().getIs_fill_in_info() == 1) {
//                    this.finish();
//                }
//            }
//        }
    }


    //保存登录的用户基本信息
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
        params.put("encrypted_password",password);
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

                                MyFragment.textPhoneNumber.setText(mobileNum);
                                finish();
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