package cn.zjh.com.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.zjh.com.myapplication.R;

/**
 * window
 *      应用window---activity
 *      子window---dialog,toast
 *      系统window---
 */
public class WindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
    }
}
