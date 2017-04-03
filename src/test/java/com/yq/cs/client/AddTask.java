package com.yq.cs.client;

import com.yq.cs.services.CalculateService;

import java.util.concurrent.CountDownLatch;

/**
 * Created by nyq on 2017/4/3.
 */
public class AddTask implements Runnable {
    private CalculateService calculateService;
    private CountDownLatch start;
    private CountDownLatch finish;
    private int a;
    private int b;
    private int time;

    public AddTask(CalculateService calculateService, CountDownLatch start, CountDownLatch finish, int a, int b, int time){
        this.calculateService = calculateService;
        this.start = start;
        this.finish = finish;
        this.a = a;
        this.b = b;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            start.await();
            while (--time>=0) {
                int result = calculateService.add(a, b);
                System.out.println(Thread.currentThread()+": add result [" + result + "]");
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            finish.countDown();
        }
    }
}
