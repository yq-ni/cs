package com.yq.cs.server;

import com.yq.cs.server.engine.NettyServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nyq on 2017/4/1.
 */
public class CSServerTest {
    public static void main(String[] args) throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("server-config.xml");
        context.getBean(NettyServer.class).start();
    }
}
