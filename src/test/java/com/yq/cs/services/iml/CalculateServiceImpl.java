package com.yq.cs.services.iml;

import com.yq.cs.services.CalculateService;

/**
 * Created by nyq on 2017/4/2.
 */
public class CalculateServiceImpl implements CalculateService {
    @Override
    public int add(int a, int b) {
        return a+b;
    }
}
