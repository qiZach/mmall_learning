package com.mmall.test;

import com.mmall.util.MD5Util;
import org.junit.Test;

public class Md5test {

    @Test
    public  void md5SaltTest(){
        System.out.println(MD5Util.MD5EncodeUtf8("161592564220180424"));
        System.out.println(MD5Util.MD5EncodeUtf8("161592564220180424"));
    }

}
