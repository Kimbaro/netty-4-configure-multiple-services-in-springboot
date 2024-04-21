package com.template.netty.model.service;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.model.ModelTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("Service_00001")
@RequiredArgsConstructor
@Slf4j
public class Service_00001 implements ModelTemplate {
    @Override
    public void execute(RequestPayload requestPayload) {
        log.info("Service_00001.class->requestPayload {}", requestPayload);
        requestPayload.getSessionHandlerContext().writeAndFlush("Hello Service_00001.class");

    }
}
