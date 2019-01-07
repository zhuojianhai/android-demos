package cn.zjh.com.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import cn.zjh.com.myapplication.R;
import cn.zjh.com.myapplication.beans.Person;
import cn.zjh.com.myapplication.utils.AesUtils;

public class NestedScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                testAes();
            }
        });
    }

    private  void testAes(){
        List<Person> personList = new ArrayList<>();
        int testMaxCount = 1000;//测试的最大数据条数
        //添加测试数据
        for (int i = 0; i < testMaxCount; i++) {
            Person person = new Person();
            person.setAge(i);
            person.setName(String.valueOf(i));
            personList.add(person);
        }
        //FastJson生成json数据
        String jsonData =    JSON.toJSONString(personList);
        Log.e("MainActivity", "AES加密前json数据 ---->" + jsonData);
        Log.e("MainActivity", "AES加密前json数据长度 ---->" + jsonData.length());

        //生成一个动态key
        String secretKey = AesUtils.generatorKey();
        Log.e("MainActivity", "AES动态secretKey ---->" + secretKey);

        //AES加密
        long start = System.currentTimeMillis();
        String encryStr = AesUtils.encrypt(secretKey, jsonData);
        long end = System.currentTimeMillis();
        Log.e("MainActivity", "AES加密耗时 cost time---->" + (end - start));
        Log.e("MainActivity", "AES加密后json数据 ---->" + encryStr);
        Log.e("MainActivity", "AES加密后json数据长度 ---->" + encryStr.length());

        //AES解密
        start = System.currentTimeMillis();
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        end = System.currentTimeMillis();
        Log.e("MainActivity", "AES解密耗时 cost time---->" + (end - start));
        Log.e("MainActivity", "AES解密后json数据 ---->" + decryStr);
    }
}
