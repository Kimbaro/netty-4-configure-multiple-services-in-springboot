package com.template.netty.model.service;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.dto.netty.ResponsePayload;
import com.template.netty.model.ModelTemplate;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component("Service_00001")
@RequiredArgsConstructor
@Slf4j
public class Service_00001 implements ModelTemplate {

    private final ResponseThreadCacheData responseThreadCacheData;

    @Override
    public void execute(RequestPayload requestPayload) {
        log.info("Service_00001.class->requestPayload {}", requestPayload);
        ResponsePayload responsePayload = ResponsePayload.builder()
                .resHeaderTextMessage("HELLO! HEADER 32bytes-----------")
                .resBodyTextMessage("HELLO! BODY 69bytes---------------Service_00001.class---------------")
                .sessionHandlerContext(requestPayload.getSessionHandlerContext())
                .build();
        responsePayload.setFullTextResTextMessage(responsePayload.getResHeaderTextMessage() + responsePayload.getResBodyTextMessage());
        responseThreadCacheData.addResponsePayload(responsePayload);

    }
}
