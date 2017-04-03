package com.yq.cs.client;

import com.yq.cs.services.CalculateService;
import com.yq.cs.services.HelloService;

import java.util.concurrent.CountDownLatch;

/**
 * Created by nyq on 2017/4/3.
 */
public class HelloTask implements Runnable {
    private HelloService helloService;
    private String message;
    private CountDownLatch start;
    private CountDownLatch finish;
    private int time;

    public HelloTask(HelloService helloService, String message, CountDownLatch start, CountDownLatch finish,int time){
        this.helloService = helloService;
        this.message = message;
        this.start = start;
        this.finish = finish;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            start.await();
            while (--time>=0) {
                String result = helloService.hello(message);
                System.out.println(Thread.currentThread()+": hello result [" + result + "]");
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
