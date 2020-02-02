package com.mmall.common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangsiqi on 2018/4/8
 */
public class OrderStatusEnumTest {
    @Test
    public void codeOf() throws Exception {
        System.out.println(Const.OrderStatusEnum.codeOf(50).getValue());
    }

}