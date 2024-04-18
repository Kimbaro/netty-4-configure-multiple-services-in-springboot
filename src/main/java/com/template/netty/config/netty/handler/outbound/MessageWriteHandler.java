package com.template.netty.config.netty.handler.outbound;

import io.netty.channel.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 쓰기 이벤트 처리부
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class MessageWriteHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("EVENT| handlerWrite:[{}]", (String)msg);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("EVENT| handlerFlush [flush]");
        ctx.disconnect();
    }

}
