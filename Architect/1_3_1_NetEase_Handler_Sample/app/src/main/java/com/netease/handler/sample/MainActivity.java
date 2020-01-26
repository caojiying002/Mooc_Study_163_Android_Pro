package com.netease.handler.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 1、Handler内存泄露测试
 * 2、为什么不能在子线程创建Handler
 * 3、textView.setText()只能在主线程执行，这句话是错误！
 * 4、new Handler()两种写法有什么区别？
 * 5、ThreadLocal用法和原理
 */
public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Message message;

    // 4、new Handler()两种写法有什么区别？
    private Handler handler1 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            startActivity(new Intent(MainActivity.this, PersonalActivity.class));
            return false;
        }
    });

    // 这是谷歌备胎的api，不推荐使用
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(msg.obj.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);
        message = new Message();

        test();
    }

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 常规的写法
//                message.obj = "Net163";
//                message.what = 163;
//                handler2.sendMessage(message);


                // 1、Handler内存泄露测试（假象）
                SystemClock.sleep(1000); // 销毁Activity
//                message.what = 3;
                // This message is already in use.
//                if (handler1 != null) handler1.sendMessage(message); // 跳转到第二个界面
                // handler1.sendMessageDelayed(message, 3000);

                // 2、为什么不能在子线程创建Handler
                // new Handler();

                // 3、textView.setText()只能在主线程执行，这句话是错误！
                // textView.setText("彭老师");
                Toast.makeText(MainActivity.this, "彭老师", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("netease >>> ", "onDestroy");

        // 不推荐写法
        message.recycle();

        handler1.removeMessages(3);
        handler1 = null;
    }
}
