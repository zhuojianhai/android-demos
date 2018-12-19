package cn.zjh.com.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zjh.com.myapplication.R;
import cn.zjh.com.myapplication.adapters.TagAdapter;
import cn.zjh.com.myapplication.views.TagFlowLayout;

public class FlexboxLayoutActivity extends DemoBaseActivity {

    @BindView(R.id.tv_recyclerview)
    TextView textView;
    TagFlowLayout tagFlowLayout;
    LayoutInflater mInflater;

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_main);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        tagFlowLayout = (TagFlowLayout) findViewById(R.id.id_tagFlowLayout);

        //tagFlowLayout 设置adapter，类似于为listView设置adapter
        tagFlowLayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public  View getView(ViewGroup parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        parent, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelect(ViewGroup parent, View view, int position)
            {
                view.setBackgroundResource(R.drawable.checked_bg);
            }

            @Override
            public void onUnSelect(ViewGroup parent, View view, int position)
            {
                view.setBackgroundResource(R.drawable.normal_bg);
            }

        });


        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlexboxLayoutActivity.this,ActivityRecyclerView.class);
                startActivity(intent);
            }
        });

    }

}
