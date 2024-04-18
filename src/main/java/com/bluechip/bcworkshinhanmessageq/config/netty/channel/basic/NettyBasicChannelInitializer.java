package com.bluechip.bcworkshinhanmessageq.config.netty.channel.basic;

import com.bluechip.bcworkshinhanmessageq.config.netty.handler.decode.ServerDecoder;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.duplex.ErrorHandler;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.duplex.SessionHandler;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.inbound.Basic2MessageHandler;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.inbound.BasicMessageHandler;
import com.bluechip.bcworkshinhanmessageq.config.netty.handler.outbound.MessageWriteHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class NettyBasicChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ServerDecoder serverDecoder = new ServerDecoder();
        ChannelPipeline pipeline = socketChannel.pipeline();

        /*=======L7 Routing=======*/
        /*duplex*/
        pipeline.addLast(new SessionHandler());

        /*inbound*/
        pipeline.addLast(serverDecoder);
        pipeline.addLast(new StringDecoder(Charset.defaultCharset()));
        pipeline.addLast(new StringEncoder(Charset.defaultCharset()));
        pipeline.addLast(new BasicMessageHandler());

        /*duplex*/
        pipeline.addLast(new ErrorHandler());

        /*outbound*/
        pipeline.addLast(new MessageWriteHandler());

    }
}
