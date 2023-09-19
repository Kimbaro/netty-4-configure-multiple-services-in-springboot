package com.bluechip.bcworkshinhanmessageq.config;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ParsingManager {
    public static void main(String[] args) {
        SimpleDateFormat dtFormat = new SimpleDateFormat("MMdd");
        Date mydate = null;
        try {
            mydate = dtFormat.parse("0304");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(mydate);
    }

    public static String subByteString(String message, int startIndex, int endIndex, Charset encoding) throws ArrayIndexOutOfBoundsException {
        /*한글이 섞인 전문 파싱할때 subString을 대체하여 사용합니다.*/
        byte[] messageToByteArray = message.getBytes(encoding);
        byte[] resultArray = new byte[Math.abs(startIndex - endIndex)];

        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = messageToByteArray[startIndex++];
        }
        return new String(resultArray, encoding).trim();
    }
}
