package com.netease.handler.core;

public class Handler {

    private Looper mLooper;
    private MessageQueue mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
    }

    public void sendMessage(Message message) {
        // 将消息放入消息队列中
        enqueueMessage(message);
    }

    private void enqueueMessage(Message message) {
        // 赋值当前handler
        message.target = this;

        // 使用mQueue，将消息放入
        mQueue.enqueueMessage(message);
    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }

    // 给开发者提供的开放API，用于重写和回调监听
    public void handleMessage(Message msg) {
    }
}
