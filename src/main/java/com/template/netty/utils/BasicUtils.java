package com.template.netty.utils;

import java.nio.charset.Charset;

public class BasicUtils {

    public static String rightSpacePadding(String src, int size, Charset charset) {

        int len = src.getBytes(charset).length;
        if(len < size) {
            for(int i=0; i < size - len; i++) {
                src = src + " ";
            }
        }
        return src;
    }

    public static String leftSpacePadding(String src, int size, Charset charset) {

        int len = src.getBytes(charset).length;
        if(len < size) {
            for(int i=0; i < size - len; i++) {
                src = " "+src;
            }
        }
        return src;
    }

    public static String leftZeroPadding(String src, int size, Charset charset) {

        int len = src.getBytes(charset).length;
        if(len < size) {
            for(int i=0; i < size - len; i++) {
                src = "0"+src;
            }
        }
        return src;
    }

    public static String rightZeroPadding(String src, int size, Charset charset) {

        int len = src.getBytes(charset).length;
        if(len < size) {
            for(int i=0; i < size - len; i++) {
                src = src+"0";
            }
        }
        return src;
    }
}
