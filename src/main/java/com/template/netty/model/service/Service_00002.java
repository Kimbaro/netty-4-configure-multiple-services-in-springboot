package com.template.netty.model.service;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.dto.netty.ResponsePayload;
import com.template.netty.model.ModelTemplate;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("Service_00002")
@RequiredArgsConstructor
@Slf4j
public class Service_00002 implements ModelTemplate {

    private final ResponseThreadCacheData responseThreadCacheData;

    @Override
    public void execute(RequestPayload requestPayload) {
        log.info("Service_00002.class->requestPayload {}", requestPayload);
        ResponsePayload responsePayload = ResponsePayload.builder()
                .resHeaderTextMessage("HELLO! HEADER 32bytes-----------")
                .resBodyTextMessage("HELLO! BODY 69bytes---------------Service_00002.class---------------")
                .sessionHandlerContext(requestPayload.getSessionHandlerContext())
                .build();
        responsePayload.setFullTextResTextMessage(responsePayload.getResHeaderTextMessage() + responsePayload.getResBodyTextMessage());
        responseThreadCacheData.addResponsePayload(responsePayload);

    }
}
