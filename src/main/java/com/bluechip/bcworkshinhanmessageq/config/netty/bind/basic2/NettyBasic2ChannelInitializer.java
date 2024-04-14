package com.bluechip.bcworkshinhanmessageq.config.netty.bind.basic2;

import com.bluechip.bcworkshinhanmessageq.config.netty.handler.decode.ServerDecoder;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.duplex.ErrorHandler;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.duplex.SessionHandler;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.inbound.Basic2MessageHandler;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.outbound.MessageWriteHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NettyBasic2ChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ServerDecoder serverDecoder = new ServerDecoder();
        ChannelPipeline pipeline = socketChannel.pipeline();
        /*duplex*/
        pipeline.addLast(new SessionHandler());
        pipeline.addLast(new ErrorHandler());

        /*inbound*/
        pipeline.addLast(serverDecoder);
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new Basic2MessageHandler());

        /*outbound*/
        pipeline.addLast(new MessageWriteHandler());

    }
}

