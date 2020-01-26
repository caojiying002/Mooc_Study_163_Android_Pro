package com.netease.handler.sample;

import org.junit.Test;

public class ThreadLocalTest {

    @Test
    public void test() {
        // 创建本地线程（主线程）
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
            @Override
            protected String initialValue() {
                // 重写初始化方法，默认返回null，如果ThreadLocalMap拿不到值再调用初始化方法
                return "冯老师";
            }
        };

        // 从ThreadLocalMap中获取String值，key是主线程
        System.out.println("主线程threadLocal：" + threadLocal.get());

        //--------------------------thread-0
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 从ThreadLocalMap中获取key：thread-0的值？没有，拿不到值再调用初始化方法
                String value1 = threadLocal.get();
                System.out.println("thread-0：" + threadLocal.get()); // 冯老师

                // ThreadLocalMap存入：key:thread-0  value:"熊老师"
                threadLocal.set("熊老师");
                String value2 = threadLocal.get(); // 可以get到了
                System.out.println("thread-0  set  >>> " + threadLocal.get()); // 熊老师

                // 使用完成建议remove()，避免大量无意义的内存占用
                threadLocal.remove();
            }
        });
        thread.start();

        //--------------------------thread-1
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 从ThreadLocalMap中获取key：thread-1的值？没有，拿不到值再调用初始化方法
                String value1 = threadLocal.get();
                System.out.println("thread-1：" + threadLocal.get()); // 冯老师

                // ThreadLocalMap存入：key:thread-1  value:"彭老师"
                threadLocal.set("彭老师");
                String value2 = threadLocal.get(); // 可以get到了
                System.out.println("thread-1  set  >>> " + threadLocal.get()); // 彭老师

                // 使用完成建议remove()，避免大量无意义的内存占用
                threadLocal.remove();
            }
        });
        thread2.start();
    }
}
