package com.bluechip.bcworkshinhanmessageq.config.netty;

import com.bluechip.bcworkshinhanmessageq.config.netty.bind.basic.NettyBasicChannelInitializer;
import com.bluechip.bcworkshinhanmessageq.config.netty.bind.basic2.NettyBasic2ChannelInitializer;
import com.bluechip.bcworkshinhanmessageq.config.netty.bind.basic3.NettyBasic3ChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class NettyServerFactory {
    @Bean(name = "basicBootstrap")
    public ServerBootstrap basicBootstrap(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new NettyBasicChannelInitializer());
        return b;
    }

    @Bean(name = "basic2Bootstrap")
    public ServerBootstrap basic2Bootstrap(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new NettyBasic2ChannelInitializer());
        return b;
    }

    @Bean(name = "basic3Bootstrap")
    public ServerBootstrap basic3Bootstrap(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new NettyBasic3ChannelInitializer());
        return b;
    }
}
