package cn.zjh.com.myapplication.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 通过三种方式实现view的滑动
 * 1》通过view本身提供的scrollTo，scrollBy方式
 * 2》通过动画给view施加平移效果
 * 3》通过改变view的LayoutParams是view重新布局从而实现滑动
 */
public class CustomView extends View {
    Scroller scroller;
    public CustomView(Context context) {
        super(context);
        scroller = new Scroller(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

    }


}
