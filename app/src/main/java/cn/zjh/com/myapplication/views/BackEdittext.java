package cn.zjh.com.myapplication.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class BackEdittext extends EditText {
    Context mContext;
    Drawable  leftIcon;
    Drawable  rightIcon;

    boolean isFocused = false;

    /**
     * 获取当前edittext是否获取焦点
     * @param focused
     * @param direction
     * @param previouslyFocusedRect
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        isFocused = focused;
        setDrawbles();
    }

    public BackEdittext(Context context) {
        super(context);
        mContext = context;
    }

    public BackEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initDrawable(int left,int right){
        leftIcon = ContextCompat.getDrawable(mContext,left);
        rightIcon = ContextCompat.getDrawable(mContext,right);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawbles();
            }
        });
        setDrawbles();
    }

    /**
     *1>如果内容大于1显示两个图标 或者没有获取焦点，那么隐藏删除图标
     *2>如果内容小于1显示1个图标
     */
    private void setDrawbles() {
        if(length() <1 || !isFocused){
            setCompoundDrawablesWithIntrinsicBounds(leftIcon,null,null,null);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(leftIcon,null,rightIcon,null);
        }
    }


    /**
     * 图片资源释放，防止内存泄漏
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        leftIcon.setCallback(null);
        rightIcon.setCallback(null);

    }

    /**
     * 如果点击的区域 大于删除图片的位置
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(rightIcon !=null && event.getAction() == MotionEvent.ACTION_UP){

            boolean isClean =(event.getX() > (getWidth() - getTotalPaddingRight()))&&
                    (event.getX() < (getWidth() - getPaddingRight()));

            if(isClean){
                setText("");
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                if(event.getX() > getWidth() - rightIcon.getIntrinsicWidth() - getPaddingEnd()){
//                    setText("");
//                }
//            }
        }
        return super.onTouchEvent(event);
    }
}
