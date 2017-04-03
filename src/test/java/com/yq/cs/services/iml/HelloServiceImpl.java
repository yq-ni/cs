package com.yq.cs.services.iml;

import com.yq.cs.services.HelloService;

/**
 * Created by nyq on 2017/4/1.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String s) {
        return "Hello " + s + "!";
    }
}
