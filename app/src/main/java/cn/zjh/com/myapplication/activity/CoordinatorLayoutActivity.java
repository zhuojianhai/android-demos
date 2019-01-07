package cn.zjh.com.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zjh.com.myapplication.R;
import cn.zjh.com.myapplication.adapters.ImageAdapter;
import cn.zjh.com.myapplication.adapters.ImageGrideAdapter;
import cn.zjh.com.myapplication.beans.RecyclerViewBean;

public class CoordinatorLayoutActivity extends DemoBaseActivity {

    @BindView(R.id.coordinator_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private ImageAdapter imageAdapter;
    private ArrayList<RecyclerViewBean> data = new ArrayList<>();
    private final String names[] = {
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };
    private final String imageUrls[] = {
            "http://hawksaloft.org/wp-content/uploads/2012/08/614612316_20090805-_mg_3411-rufous-hummingbird-5x7.jpg",
            "http://www.gregscott.com/gjs_2007_spring/hummingbird/20070311_1948_100_0560.rufous_humminbird.jpg",
            "http://mosthdwallpapers.com/wp-content/uploads/2016/06/Flying-Hummingbird-Pictures.jpg",
            "https://wallpapercave.com/wp/alkKAoC.jpg",
            "http://mosthdwallpapers.com/wp-content/uploads/2016/06/Gorgeous-Hummingbird-Wallpapers-For-Desktop.jpg",
            "http://naturecanada.ca/wp-content/uploads/2014/07/Ruby-throat-Hummingbird-shutterstock_1953533.jpg",
            "http://images5.fanpop.com/image/photos/26100000/Hummingbird-hummingbirds-26167630-1024-740.jpg\n",
            "https://farm5.staticflickr.com/4065/4698051727_5024cd4e6c_b.jpg",
            //可以加载gif格式图片
            "http://p1.pstatp.com/large/166200019850062839d3",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.coordinator_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initDatas();
    }

    private void initDatas() {
        for (int i = 0; i < names.length; ++i) {
            RecyclerViewBean recyclerViewBean = new RecyclerViewBean();
            recyclerViewBean.setImgUrl(imageUrls[i]);
            recyclerViewBean.setName(names[i]);
            data.add(recyclerViewBean);
        }

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        StaggeredGridLayoutManager gridLayoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);



        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(gridLayoutManager);

        imageAdapter = new ImageAdapter(this,data);

        mRecyclerView.setAdapter(imageAdapter);

        //为recyclerView 的Item 设置点击事件
//        imageGrideAdapter.setOnItemClickListener(new ImageGrideAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerViewBean data) {
//                Toast.makeText(ActivityRecyclerView.this, "点击-> "+ data.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.getTabAt(0).setText("头条");
        tabLayout.getTabAt(1).setText("热点");
        tabLayout.getTabAt(2).setText("娱乐");
        tabLayout.getTabAt(3).setText("美女");
    }

}
