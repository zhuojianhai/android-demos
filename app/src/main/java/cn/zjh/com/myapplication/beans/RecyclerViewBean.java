package cn.zjh.com.myapplication.beans;

import java.io.Serializable;

/**
 * Created by zhuojh on 2018/12/18.
 *
 */

public class RecyclerViewBean implements Serializable {
    private String name;
    private String imgUrl;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RecyclerViewBean(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }








}
