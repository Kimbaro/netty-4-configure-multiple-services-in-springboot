package com.template.netty.config.netty.channel.basic2;

import com.template.netty.config.netty.handler.decode.ServerDetailDecoder;
import com.template.netty.config.netty.handler.duplex.ErrorHandler;
import com.template.netty.config.netty.handler.duplex.SessionHandler;
import com.template.netty.config.netty.handler.inbound.Basic2MessageHandler;
import com.template.netty.config.netty.handler.outbound.MessageWriteHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
@Slf4j
@RequiredArgsConstructor
public class NettyBasic2ChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel){
        ServerDetailDecoder serverDetailDecoder = new ServerDetailDecoder();
        ChannelPipeline pipeline = socketChannel.pipeline();

        /*=======L7 Routing=======*/
        /*duplex*/
        pipeline.addLast(new SessionHandler());

        /*inbound*/
        pipeline.addLast(serverDetailDecoder);
        pipeline.addLast(new StringDecoder(Charset.defaultCharset()));
        pipeline.addLast(new StringEncoder(Charset.defaultCharset()));
        pipeline.addLast(new Basic2MessageHandler());

        /*duplex*/
        pipeline.addLast(new ErrorHandler());

        /*outbound*/
        pipeline.addLast(new MessageWriteHandler());
    }
}

