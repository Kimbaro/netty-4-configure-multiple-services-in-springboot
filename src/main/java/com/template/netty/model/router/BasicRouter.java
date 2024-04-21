package com.template.netty.model.router;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.dto.netty.ResponsePayload;
import com.template.netty.model.ModelTemplate;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("BasicRouter")
@RequiredArgsConstructor
@Slf4j
public class BasicRouter implements ModelTemplate {

    private final ResponseThreadCacheData responseThreadCacheData;

    @Override
    public void execute(RequestPayload requestPayload) {
        log.info("BasicRouter.class->requestPayload {}", requestPayload);
        ResponsePayload responsePayload = ResponsePayload.builder()
                .resHeaderTextMessage("HELLO! HEADER 32bytes-----------")
                .resBodyTextMessage("HELLO! BODY 69bytes----------------BasicRouter.class----------------")
                .sessionHandlerContext(requestPayload.getSessionHandlerContext())
                .build();
        responsePayload.setFullTextResTextMessage(responsePayload.getResHeaderTextMessage() + responsePayload.getResBodyTextMessage());
        responseThreadCacheData.addResponsePayload(responsePayload);
    }
}
