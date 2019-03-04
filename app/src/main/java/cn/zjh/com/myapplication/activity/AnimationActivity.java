package cn.zjh.com.myapplication.activity;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.zjh.com.myapplication.R;


/**
 * 给Button设置个动画，
 * 让widht从当前宽度，增加到500px
 *
 *
 * 1》给你的对象增加get、set方法，如果你有权限
 * 2》用一个类来包装原始对象，间接提供get、set方法
 * 3》采用valueAnimator，监听动画过程，自己实现属性的变化
 */
public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AnimationActivity";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();
    }

    private void initView() {

         button = findViewById(R.id.anmation_btn);
        button.setOnClickListener(this);

    }

    private void performAnimator(){
        ViewWraps wraps = new ViewWraps(button);
        ObjectAnimator.ofInt(wraps,"width",800).setDuration(2000).start();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Button button = (Button)findViewById(R.id.anmation_btn);
            performAnimate(button, button.getWidth(), 500);
        }
    }

    private void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                Log.d(TAG, "current value: " + currentValue);

                // 获得当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = animator.getAnimatedFraction();
                // 直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(5000).start();
    }

    @Override
    public void onClick(View v) {
        if(v==button){
            performAnimator();
        }
    }

    private static class ViewWraps{
        private View mTarget;
        public ViewWraps(View mTarget){
            this.mTarget = mTarget;
        }

        public int getWidth(){
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width){
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}
