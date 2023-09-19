package com.bluechip.bcworkshinhanmessageq.modules.netty.handler.duplex;

import com.bluechip.bcworkshinhanmessageq.modules.netty.handler.inbound.MessageServiceHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 세션 이벤트 처리부
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class SessionHandler extends ChannelDuplexHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String remoteAddress = ctx.channel().remoteAddress().toString();
        log.info("EVENT->channelActive Remote Address: " + remoteAddress);
    }
}
