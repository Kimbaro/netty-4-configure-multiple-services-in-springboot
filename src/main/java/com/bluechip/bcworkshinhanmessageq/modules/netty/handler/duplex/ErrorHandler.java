package com.bluechip.bcworkshinhanmessageq.modules.netty.handler.duplex;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**에러 이벤트 처리부*/
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ErrorHandler extends ChannelDuplexHandler {
    // 클라이언트와 연결되어 트래픽을 생성할 준비가 되었을 때 호출되는 메소드
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("EVENT->exceptionCaught");
        // Close the connection when an exception is raised.
        ctx.close();
        cause.printStackTrace();
    }
}