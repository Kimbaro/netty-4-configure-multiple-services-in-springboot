package com.template.netty.modules.threadHandler.response;

import com.sun.jdi.connect.spi.ClosedConnectionException;
import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.dto.netty.ResponsePayload;
import com.template.netty.modules.threadHandler.request.RequestThreadCacheData;
import com.template.netty.modules.threadHandler.service.ServiceThreadCacheData;
import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseThreadHandler {

    private final RequestThreadCacheData requestThreadCacheData;
    private final ResponseThreadCacheData responseThreadCacheData;
    private final ServiceThreadCacheData serviceThreadCacheData;

    @Value("${server-setup.thread-delay.response}")
    private int delay;

    @Async("responseThreadPool")
    public void start() {
        ResponsePayload responsePayload = null;
        while (true) {
            try {
                responsePayload = responseThreadCacheData.getResponsePayload();
                if (responsePayload == null) {
                    sleep(delay);
                    continue;
                }
                execute(responsePayload);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ClosedConnectionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void execute(ResponsePayload responsePayload) throws ClosedConnectionException {
        log.info("\nRESPONSE-THREAD-POOL {} \n| execute \n| CURRENT-CACHE {}", currentThread().getName(), responsePayload);

        if (responsePayload.getSessionHandlerContext() == null || !responsePayload.getSessionHandlerContext().channel().isActive()) {
            throw new ClosedConnectionException("클라이언트가 연결을 끊었음");
        }
        byte[] btsBody = responsePayload.getFullTextResTextMessage().getBytes(Charset.defaultCharset());
        ByteBuf buf = responsePayload.getSessionHandlerContext().alloc().buffer(btsBody.length);
        buf.writeBytes(btsBody);
        responsePayload.getSessionHandlerContext().writeAndFlush(buf);
        responsePayload.getSessionHandlerContext().disconnect();
    }
}
