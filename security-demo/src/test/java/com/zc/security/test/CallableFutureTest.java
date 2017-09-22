package com.zc.security.test;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/9/22 0022.
 */
public class CallableFutureTest {

    static ExecutorService mExecutor = Executors.newFixedThreadPool(5);


    public static CallableFutureTest main = new CallableFutureTest();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("111");
        Future f = main.future_1();
        System.out.println("2222");

        Thread.sleep(4000);
        System.out.println("33333");
        f.get();

        System.out.println("44444");
    }


    public Future future_1() {
        Future<String> future = mExecutor.submit(() -> {
            System.out.println("future start ....");
            Thread.sleep(5000);
            System.out.println("future end ....");
            return "zhongc";
        });
        return future;
    }


    public void future() {
        Future<String> future = (Future<String>) mExecutor.submit(() -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("asdad");
        });

    }
}
