package com.template.netty.config.netty.handler.inbound;

import com.template.netty.dto.netty.RequestPayload;
import com.template.netty.modules.threadHandler.request.RequestThreadCacheData;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import com.template.netty.modules.threadHandler.service.ServiceThreadCacheData;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 메시지에 대한 비지니스로직 수행
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class BasicMessageHandler extends ChannelInboundHandlerAdapter {

    private RequestThreadCacheData requestThreadCacheData;
    private ResponseThreadCacheData responseThreadCacheData;
    private ServiceThreadCacheData serviceThreadCacheData;

    public BasicMessageHandler(RequestThreadCacheData requestThreadCacheData, ResponseThreadCacheData responseThreadCacheData, ServiceThreadCacheData serviceThreadCacheData) {
        this.requestThreadCacheData = requestThreadCacheData;
        this.responseThreadCacheData = responseThreadCacheData;
        this.serviceThreadCacheData = serviceThreadCacheData;
    }

    private String buff;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg != null) {
            RequestPayload requestPayload = (RequestPayload) msg;
            log.info("NETTY-EVENT->channelRead:{}", requestPayload.toString());
            requestThreadCacheData.addRequestPayload(requestPayload);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("NETTY-EVENT| handlerRemoved [end]");
        try {
            if (!buff.isEmpty() || !buff.isBlank()) {
                buff = null;
            }
        } catch (NullPointerException e) {
            log.info("NETTY-EVENT| handlerRemoved [buff empty success]");
        } finally {
            buff = null;
        }
    }
}
