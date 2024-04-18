package com.template.netty.config.netty.handler.inbound;

import com.template.netty.dto.netty.RequestPayload;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 메시지에 대한 비지니스로직 수행
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class BasicMessageHandler extends ChannelInboundHandlerAdapter {

    private String buff;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg != null) {
            RequestPayload requestPayload = (RequestPayload) msg;
            log.info("EVENT->channelRead:{}", requestPayload.toString());
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("EVENT| handlerRemoved [end]");
        try {
            if (!buff.isEmpty() || !buff.isBlank()) {
                buff = null;
            }
        } catch (NullPointerException e) {
            log.info("EVENT| handlerRemoved [buff empty success]");
        } finally {
            buff = null;
        }
    }
}
