package com.homework.ts.ui.activity;

/**
 * Created by ts on 2017/3/25.
 */

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.homework.ts.util.Constant;
import com.homework.ts.util.MyCounts;
import com.homework.ts.util.UtilMethod;
import com.homework.ts.zijixi.BaseActivity;
import com.homework.ts.zijixi.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginForgetPasswordActivity extends BaseActivity {

    private EditText editTextPhome, editTextVerCode, editTextPassword;
    private Button mButton;
    private ImageView mImageView;
    private TextView mTextGetSMSCode;

    private boolean isShowPwd = false;
    private boolean isCheckMobileNum = false;
    private boolean isGetVerCode = false;
    private MyCounts myCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
//        initSystemBar(container);
        initToolBar("忘记密码");

        initView();

        myCount = new MyCounts(getApplicationContext(),60000, 1000, mTextGetSMSCode);
    }

    private void initView() {

        editTextPhome = (EditText) findViewById(R.id.edittext_phone);
        editTextVerCode = (EditText) findViewById(R.id.edittext_ver_code);
        editTextPassword = (EditText) findViewById(R.id.edit_new_password);

        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.imgaeview_pwd);
        mTextGetSMSCode = (TextView) findViewById(R.id.textView);

        mButton.setOnClickListener(listener);
        mImageView.setOnClickListener(listener);
        mTextGetSMSCode.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String phone = editTextPhome.getText().toString();
            String verCode = editTextVerCode.getText().toString();
            String password = editTextPassword.getText().toString();

            int pwdlength = password.length();

            password = UtilMethod.parseStrToMd5L32(password);
            if (v == mButton) {
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(LoginForgetPasswordActivity.this.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                if (UtilMethod.isMobileNO(phone)) {
                    if (verCode.length()>=4) {
                        if (pwdlength >= 6&&pwdlength<=30) {
//                            checkPhone2(phone, password, verCode);

                            finish();
                        } else {
                            showToast("密码的长度应为6~30位之间");
                        }
                    } else {
                        showToast("验证码不正确!");
                    }
                } else {
                    showToast("请输入正确的手机号");
                }

            }

            if (v == mImageView) {
                setShowPwd();
            }

            if (v == mTextGetSMSCode) {
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(LoginForgetPasswordActivity.this.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                if (UtilMethod.isMobileNO(phone)) {
                    isGetVerCode = true;
//                    checkPhone(phone);
                    finish();
                } else {
                    showToast("请输入正确的手机号");
                }
            }
        }
    };

    private void setShowPwd() {
        if (isShowPwd) {
            editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mImageView.setImageResource(R.mipmap.icon_show_pw);
        } else {
            mImageView.setImageResource(R.mipmap.icon_hide_pw);
            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
        isShowPwd = !isShowPwd;
        //切换后将EditText光标置于末尾
        CharSequence charSequence = editTextPassword.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
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

    private void setPassword(String phone, String password,String verCode) {
        String url = Constant.MY_UTL + "login/modify_use_phone?mobilePhoneNumber=" + phone + "&&ver_code=" + verCode+"&&password="+password;

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int resultCode = 0;
                        try {
                            resultCode = response.getInt("code");
                            if(resultCode==1){
                                showToast("设置成功");
                                LoginForgetPasswordActivity.this.finish();
                            }else{
                                showToast("验证码不正确");
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        });
        Constant.queue.add(jsonObjRequest);
    }
}

