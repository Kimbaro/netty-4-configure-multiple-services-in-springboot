package com.template.netty.config.netty.channel.basic;

import com.template.netty.config.netty.handler.decode.ServerDecoder;
import com.template.netty.config.netty.handler.duplex.ErrorHandler;
import com.template.netty.config.netty.handler.duplex.SessionHandler;
import com.template.netty.config.netty.handler.inbound.BasicMessageHandler;
import com.template.netty.config.netty.handler.outbound.MessageWriteHandler;
import com.template.netty.modules.threadHandler.request.RequestThreadCacheData;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import com.template.netty.modules.threadHandler.service.ServiceThreadCacheData;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
@Slf4j
@RequiredArgsConstructor
public class NettyBasicChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RequestThreadCacheData requestThreadCacheData;
    private ResponseThreadCacheData responseThreadCacheData;
    private ServiceThreadCacheData serviceThreadCacheData;

    public NettyBasicChannelInitializer(RequestThreadCacheData requestThreadCacheData,
                                        ResponseThreadCacheData responseThreadCacheData,
                                        ServiceThreadCacheData serviceThreadCacheData) {
        this.requestThreadCacheData = requestThreadCacheData;
        this.responseThreadCacheData = responseThreadCacheData;
        this.serviceThreadCacheData = serviceThreadCacheData;
    }

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
        pipeline.addLast(new BasicMessageHandler(requestThreadCacheData, responseThreadCacheData, serviceThreadCacheData));

        /*duplex*/
        pipeline.addLast(new ErrorHandler());

        /*outbound*/
        pipeline.addLast(new MessageWriteHandler());

    }
}
