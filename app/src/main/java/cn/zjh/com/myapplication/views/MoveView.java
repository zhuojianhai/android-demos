package cn.zjh.com.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.zjh.com.myapplication.R;

/**
 * Created by zhuojh on 2019/2/22.
 */

public class MoveView extends View {

    int color;
    int mLastX = 0;
    int mLastY = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public MoveView(Context context) {
        super(context);
        init();
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MoveView);
        color = ta.getColor(R.styleable.MoveView_circle_color, Color.GREEN);
        ta.recycle();
        init();
    }

    private void init() {
        paint.setColor(color);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth =0;
        int measuredHeight = 0;

        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        //如果父容器对子容器宽高都不做限制，那么默认给个大小
        if(widthMeasureMode == MeasureSpec.AT_MOST
                && heightMeasureMode == MeasureSpec.AT_MOST ){
            measuredWidth =200;
            measuredHeight = 200;
            setMeasuredDimension(measuredWidth,measuredHeight);
        }else if(widthMeasureMode == MeasureSpec.AT_MOST ){
            measuredWidth =200;
            measuredHeight =heightMeasureSize;
            setMeasuredDimension(measuredWidth,measuredHeight);
        }else if(heightMeasureMode  == MeasureSpec.AT_MOST  ){
            measuredWidth =widthMeasureSize;
            measuredHeight =200;
            setMeasuredDimension(measuredWidth,measuredHeight);
        }

        setMeasuredDimension(measuredWidth,measuredHeight);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingLeft();//getPaddingRight();
        int paddingTop = getPaddingLeft();//getPaddingTop();
        int paddingBottom = getPaddingLeft();//getPaddingBottom();

        int width = getWidth() -paddingLeft -paddingRight;
        int height = getHeight() - paddingTop -paddingBottom;

        int radius = Math.min(width,height)/2;

        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,radius,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获得手指触发点的X,Y坐标
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = currentX;
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:
                //获得移动的偏移x，y值
                int offsetX = (int) (event.getX() - mLastX);
                int offsetY = (int) (event.getY()- mLastY);


//                layout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);
                moveByLayout(getLeft()+offsetX,getTop()+offsetY,getRight()+offsetX,getBottom()+offsetY);

//                moveByoffSetDirection(offsetX,offsetY);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * 调用layout方法，重新放置view的位置
     * view 绘制的时候通过onLayout方法设置显示位置，因此可以使用这种方式
     * @param left Left position, relative to parent
     * @param top Top position, relative to parent
     * @param right Right position, relative to parent
     * @param bottom Bottom position, relative to parent
     */
    private void moveByLayout(int left,int top,int right,int bottom){
        layout(left,top,right,bottom);
    }


    private void moveByoffSetDirection(int offSetX,int offSetY){
//        Offset this view's horizontal location by the specified amount of pixels.
        offsetLeftAndRight(offSetX);

//        Offset this view's vertical location by the specified number of pixels.
        offsetTopAndBottom(offSetY);
    }




    /**
     * 通过ViewGroupMarginParams方式改变view位置
     * @param offsetX
     * @param offsetY
     */
    private void moveByViewGroupMarginParams(int offsetX,int offsetY){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
        params.leftMargin = getLeft()+offsetX;
        params.topMargin = getTop() +offsetY;
        setLayoutParams(params);
    }
}
