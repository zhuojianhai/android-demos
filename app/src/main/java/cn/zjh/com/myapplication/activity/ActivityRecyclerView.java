package cn.zjh.com.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zjh.com.myapplication.R;
import cn.zjh.com.myapplication.adapters.ImageAdapter;
import cn.zjh.com.myapplication.beans.RecyclerViewBean;

public class ActivityRecyclerView extends DemoBaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

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
            "http://mosthdwallpapers.com/wp-content/uploads/2016/06/Beautiful-Hummingbird-HD-Photography.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_recycler_view);
        ButterKnife.bind(this);


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
        mRecyclerView.setLayoutManager(layoutManager);

        imageAdapter = new ImageAdapter(this,data);
        mRecyclerView.setAdapter(imageAdapter);



    }

}
