package cn.zjh.com.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.zjh.com.myapplication.R;
import cn.zjh.com.myapplication.beans.RecyclerViewBean;

/**
 * Created by zhuojh on 2018/12/18.
 */

public class ImageGrideAdapter extends RecyclerView.Adapter<ImageGrideAdapter.ImageViewHolder> {
    private Context context;
    private ArrayList<RecyclerViewBean> data;



    public ImageGrideAdapter(Context context, ArrayList<RecyclerViewBean> data){
        this.context = context;
        this.data = data;
    }



    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_grid_item,viewGroup,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int position) {
        RecyclerViewBean item = data.get(position);
        imageViewHolder.textView.setText(item.getName());
        Glide.with(context).load(item.getImgUrl()).into(imageViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

     public    ImageView imageView;
     public    TextView textView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            //适配器构造时只会用到实体类的get方法，用以获取相应的属性
            imageView = itemView.findViewById(R.id.recycler_image);
            textView = itemView.findViewById(R.id.recycler_img_name);
        }
    }
}
