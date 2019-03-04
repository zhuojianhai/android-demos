package cn.zjh.com.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zjh.com.myapplication.views.MyDrawerLayout;


/***
 * 1.static变量引起的内存泄漏
 因为static变量的生命周期是在类加载时开始 类卸载时结束，也就是说static变量是在程序进程死亡时才释放，如果在static变量中 引用了Activity 那么 这个Activity由于被引用，便会随static变量的生命周期一样，一直无法被释放，造成内存泄漏。

 解决办法：
 在Activity被静态变量引用时，使用 getApplicationContext 因为Application生命周期从程序开始到结束，和static变量的一样。

 2.线程造成的内存泄漏
 类似于上述例子中的情况，线程执行时间很长，及时Activity跳出还会执行，因为线程或者Runnable是Acticvity内部类，因此握有Activity的实例(因为创建内部类必须依靠外部类)，因此造成Activity无法释放。
 AsyncTask 有线程池，问题更严重

 解决办法：
 1.合理安排线程执行的时间，控制线程在Activity结束前结束。
 2.将内部类改为静态内部类，并使用弱引用WeakReference来保存Activity实例 因为弱引用 只要GC发现了 就会回收它 ，因此可尽快回收

 3.BitMap占用过多内存
 bitmap的解析需要占用内存，但是内存只提供8M的空间给BitMap，如果图片过多，并且没有及时 recycle bitmap 那么就会造成内存溢出。

 解决办法：
 及时recycle 压缩图片之后加载图片

 4.资源未被及时关闭造成的内存泄漏
 比如一些Cursor 没有及时close 会保存有Activity的引用，导致内存泄漏

 解决办法：
 在onDestory方法中及时 close即可

 5.Handler的使用造成的内存泄漏
 由于在Handler的使用中，handler会发送message对象到 MessageQueue中 然后 Looper会轮询MessageQueue 然后取出Message执行，但是如果一个Message长时间没被取出执行，那么由于 Message中有 Handler的引用，而 Handler 一般来说也是内部类对象，Message引用 Handler ，Handler引用 Activity 这样 使得 Activity无法回收。

 解决办法：
 依旧使用 静态内部类+弱引用的方式 可解决

 6.带参数的单例



 如果我们在在调用Singleton的getInstance()方法时传入了Activity。那么当instance没有释放时，这个Activity会一直存在。因此造成内存泄露。
 解决方法：

 可以将new Singleton(context)改为new Singleton(context.getApplicationContext())即可，这样便和传入的Activity没关系了。
 */
public class MainActivity extends DemoBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    @BindView(R.id.content_img)
    ImageView imageView;

    @BindView(R.id.m_btn_1)
    Button button1;

    @BindView(R.id.m_btn_2)
    Button button2;

    @BindView(R.id.drawer_layout)
    MyDrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,ActivityRecyclerView.class);
                Intent intent = new Intent(MainActivity.this,FlexboxLayoutActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        initClickListeners();
    }

    private void initClickListeners() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        /**
         * ==============这段代码会导致内存泄漏======
         */
   final   Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
        /**
         * ==============这段代码会导致内存泄漏======
         */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == button1){
            Intent intent = new Intent(MainActivity.this,ProcessButtonActivity.class);
            startActivity(intent);
        }else if(v==button2){
            Intent intent = new Intent(MainActivity.this,ImageSliderActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
