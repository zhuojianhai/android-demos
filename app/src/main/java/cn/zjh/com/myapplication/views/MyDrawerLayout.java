package cn.zjh.com.myapplication.views;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhuojh on 2018/12/18.
 */

public class MyDrawerLayout extends DrawerLayout {
    View mContent;
    ViewGroup mMenu;

    GestureDetector gestureDetector;
    final  int menuPadding = 300;



    private boolean disallowSliding = false;

    public void setDisallowSliding(boolean disallowSliding) {
        this.disallowSliding = disallowSliding;
    }


    public MyDrawerLayout(Context context) {
        super(context);
        init(context);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context){
        gestureDetector = new GestureDetector(context,new MDGesture());
        addDrawerListener(new MDDrawerListener());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return gestureDetector.onTouchEvent(ev)||super.dispatchTouchEvent(ev);
    }


    /**
     * 控件只有在测量阶段初始化，在构造方法初始化，会报空指针异常
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mContent= getChildAt(0);
        mMenu = (ViewGroup)getChildAt(1);

        View mMenuLayout = mMenu.getChildAt(0);
        mMenuLayout.getLayoutParams().width = getMeasuredWidth();

        mMenu.scrollTo(-menuPadding,0);
    }

    private class MDDrawerListener extends SimpleDrawerListener{
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            int padding = (int) -(menuPadding*(1-slideOffset));
            drawerView.scrollTo(padding,0);
            //让内容向右侧平移
            mContent.setTranslationX(mMenu.getMeasuredWidth()*slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
        }

    }



    private  class MDGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(disallowSliding){
                return false;
            }

            int startX = (int)e1.getX();
            int endX = (int)e2.getX();

            //检查是否打开状态
            boolean isDrawerOpen = isDrawerOpen(Gravity.START);

            //检查到是往右侧滑动
            if(startX-endX <0 && velocityX>100 &&  !isDrawerOpen){
                openDrawer(Gravity.START);
                return true;
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
