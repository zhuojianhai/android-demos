package cn.zjh.com.myapplication.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HandlerActivity extends AppCompatActivity {
    private static final String TAG = "HandlerActivity";

    private Button send;
    private TextView textView;
    /**
     * 1》推送未来某个时间点将要执行的Message或者Runnable到消息队列中
     * 2》在子线程把需要在另一个线程执行的操作加入到消息队列中去
     */
    private final static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "received msg is " + msg.obj);
        }
    };

    private static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        send = findViewById(R.id.send_msg);
        textView = findViewById(R.id.show_info);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message ms = handler.obtainMessage();
                ms.obj = "我是主线程发送的消息";
                handler.sendMessage(ms);

                showChildThread();
            }
        });
    }

    private void showChildThread() {

        new Thread("child thread 01") {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                h = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.e(TAG, "received message is " + msg.obj);

                        textView.setText(msg.obj.toString());
                    }
                };

                Message ms = h.obtainMessage();
                ms.obj = "我是子线程发送的消息";
                h.sendMessage(ms);

                MessageQueue queue = handler.getLooper().getQueue();

                Log.e(TAG,"mainThread queue address is :"+queue.toString());

                MessageQueue cqueue = h.getLooper().getQueue();

                Log.e(TAG,"childThread queue address is: "+cqueue.toString());

                if (queue.toString().equals(cqueue.toString())) {
                    Log.e(TAG, "主线程的queue和子线程的queue 是相等额");
                } else {
                    Log.e(TAG, "主线程的queue和子线程的queue 是不相等额");
                }

                Looper.loop();


            }
        }.start();
    }
}
