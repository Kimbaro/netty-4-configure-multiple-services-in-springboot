package com.template.netty.config.netty.handler.outbound;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
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
public class MessageWriteFlushHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("NETTY-EVENT| handlerWrite:[{}]", (String)msg);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("NETTY-EVENT| handlerFlush [flush]");
        ctx.disconnect();
    }
}
