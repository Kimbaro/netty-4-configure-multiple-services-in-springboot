package com.template.netty.modules;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * NettyFramework Start
 *
 * */

@Component
@Slf4j
@RequiredArgsConstructor
public class NettyStartupModule implements ApplicationListener<ApplicationReadyEvent> {

    @Qualifier(value = "basicBootstrap")
    private final ServerBootstrap basicBootstrap;

    @Qualifier(value = "basic2Bootstrap")
    private final ServerBootstrap basic2Bootstrap;

    @Value("${netty-ports.basic}")
    private int basicPort;

    @Value("${netty-ports.basic2}")
    private int basic2Port;


    private final ConfigurableApplicationContext context;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        /*Netty Server Start Sequence*/
        ChannelFuture  basicChannelFuture = null;
        ChannelFuture  basic2ChannelFuture = null;
        try {
            basicChannelFuture = basicBootstrap.bind(basicPort).sync();
            if(basicChannelFuture.isSuccess()){
                log.info("HelloWorld! Netty server is running on port {}", basicPort);
            }

            basic2ChannelFuture = basic2Bootstrap.bind(basic2Port).sync();
            if(basic2ChannelFuture.isSuccess()){
                log.info("HelloWorld! Netty server is running on port {}", basic2Port);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.close();
            System.exit(0);
        }
    }
}
