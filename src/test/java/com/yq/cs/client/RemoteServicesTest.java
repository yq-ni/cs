package com.yq.cs.client;

import com.yq.cs.client.engine.OnReceiveListener;
import com.yq.cs.message.SerializeProtocol;
import com.yq.cs.services.CalculateService;
import com.yq.cs.services.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by nyq on 2017/4/1.
 */
public class RemoteServicesTest {

    public static void parallelTest(HelloService helloService, CalculateService calculateService, int num, int time) throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(num*2);
        for (int i = 0; i < num; i++) {
            new Thread(new AddTask(calculateService, start, finish, i, i, time)).start();
            new Thread(new HelloTask(helloService, "yq", start, finish, time)).start();
        }
        long s = System.currentTimeMillis();
        start.countDown();
        finish.await();
        System.out.println("finish at " + (System.currentTimeMillis()-s) + "ms");
    }

    public static void asyncTest(RemoteServices remoteServices, int time) throws Exception {
        CountDownLatch finish = new CountDownLatch(time*2);
        OnReceiveListener onReceiveListener = new OnReceiveListener() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("asyncTest success: "+ result);
                finish.countDown();
            }
            @Override
            public void onFail(Object error) {
                System.out.println("asyncTest fail: "+ error);
                finish.countDown();
            }
        };
        long s = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            remoteServices.callAsync(onReceiveListener, HelloService.class, "hello", "yq");
            remoteServices.callAsync(onReceiveListener, CalculateService.class, "add", i, i);
        }
        finish.await();
        System.out.println("finish in " + (System.currentTimeMillis()-s) + "ms");
    }

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("client-config.xml");
//        CalculateService c = context.getBean(CalculateService.class);
//        HelloService h = context.getBean(HelloService.class);
//        parallelTest(h, c, 1000, 2);
        asyncTest(context.getBean(RemoteServices.class), 10000);
        context.destroy();
    }

    public static void normalTest() throws Exception {

        RemoteServices r = new RemoteServices();

        String ipAddr8080 = "127.0.0.1:8080";
        String ipAddr8081 = "127.0.0.1:8081";

        ServiceProperty serviceProperty1 = new ServiceProperty();
        serviceProperty1.setIpAddr(ipAddr8080);
        serviceProperty1.setInterface(HelloService.class);
        ServiceProperty serviceProperty2 = new ServiceProperty();
        serviceProperty2.setIpAddr(ipAddr8081);
        serviceProperty2.setInterface(CalculateService.class);

        r.registerService(serviceProperty1);
        r.registerService(serviceProperty2);

        ClientIPAddrConfig config1 = new ClientIPAddrConfig();
        config1.setLazy(false);
        config1.setProtocol(Enum.valueOf(SerializeProtocol.class, "KRYO"));
        config1.setIpAddr(ipAddr8080);
        ClientIPAddrConfig config2 = new ClientIPAddrConfig();
        config2.setLazy(false);
        config2.setProtocol(Enum.valueOf(SerializeProtocol.class, "JDK"));
        config2.setIpAddr(ipAddr8081);

        r.registerConfig(config1);
        r.registerConfig(config2);

        CalculateService c = r.getProxy(CalculateService.class);
        HelloService h = r.getProxy(HelloService.class);

        parallelTest(h,c, 1000, 2);

        r.destroy();
    }
}
