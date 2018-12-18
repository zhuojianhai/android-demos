package cn.zjh.com.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.zjh.com.myapplication.R;

public class DemoBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_base);
    }
}
