package com.template.netty.model.router;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.model.ModelTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("BasicRouter")
@RequiredArgsConstructor
@Slf4j
public class BasicRouter implements ModelTemplate {
    @Override
    public void execute(RequestPayload requestPayload) {
        log.info("BasicRouter.class->requestPayload {}", requestPayload);
        requestPayload.getSessionHandlerContext().writeAndFlush("Hello BasicRouter.class");
    }
}
