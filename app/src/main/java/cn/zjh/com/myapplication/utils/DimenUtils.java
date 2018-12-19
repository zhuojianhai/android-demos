package cn.zjh.com.myapplication.utils;

import android.content.Context;

/**
 * Created by zhuojh on 2018/12/19.
 */

public class DimenUtils {

    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
