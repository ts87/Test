package com.homework.ts.util;

/**
 * Created by ts on 2017/3/25.
 */

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.homework.ts.zijixi.R;


public class MyCounts extends CountDownTimer {
    private TextView textView;
    private Context mContext;
    public MyCounts(long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }
    public MyCounts(Context mContext,long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.mContext = mContext;
    }
    @Override
    public void onFinish() {
        textView.setText("重新获取");
        textView.setEnabled(true);
        textView.setBackgroundColor(mContext.getResources().getColor(R.color.light_blue));
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText("请等待" + millisUntilFinished / 1000 + "秒");
        textView.setEnabled(false);
        textView.setBackgroundColor(mContext.getResources().getColor(R.color.item_gray));
    }
}
