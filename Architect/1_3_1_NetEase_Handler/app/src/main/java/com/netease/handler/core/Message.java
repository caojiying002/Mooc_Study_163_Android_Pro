package com.netease.handler.core;

// 消息对象
public class Message {

    // 标识
    public int what;
    // Handler对象
    public Handler target;
    // 消息内容
    public Object obj;

    public Message() {
    }

    public Message(Object obj) {
        this.obj = obj;
    }

    // 模拟
    @Override
    public String toString() {
        return obj.toString();
    }
}
