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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homework.ts.util.UtilMethod;
import com.homework.ts.view.CircleImageView;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONObject;

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
                case R.id.button:
                    final String phone = editTextPhone.getText().toString();
                    String password = editTextPhone.getText().toString();

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
//                        phoneLogin(phone, password);

                        //登录！！！！！！！！！
                        finish();
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

}