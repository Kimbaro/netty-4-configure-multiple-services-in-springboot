package com.bluechip.bcworkshinhanmessageq.modules;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.BindException;

/**
 *
 * NettyFramework Start
 *
 * */

@Component
@Slf4j
@RequiredArgsConstructor
public class NettyStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Qualifier(value = "basicBootstrap")
    private final ServerBootstrap basicBootstrap;

    @Qualifier(value = "basic2Bootstrap")
    private final ServerBootstrap basic2Bootstrap;

    @Qualifier(value = "basic3Bootstrap")
    private final ServerBootstrap basic3Bootstrap;

    @Value("${netty-ports.basic}")
    private int basicPort;

    @Value("${netty-ports.basic2}")
    private int basic2Port;

    @Value("${netty-ports.basic3}")
    private int basic3Port;



    private final ConfigurableApplicationContext context;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        /*Netty Server Start Sequence*/
        ChannelFuture  basicChannelFuture = null;
        ChannelFuture  basic2ChannelFuture = null;
        ChannelFuture  basic3ChannelFuture = null;
        try {
            basicChannelFuture = basicBootstrap.bind(basicPort).sync();
            if(basicChannelFuture.isSuccess()){
                log.info("HelloWorld! Netty server is running on port {}", basicPort);
            }

            basic2ChannelFuture = basic2Bootstrap.bind(basic2Port).sync();
            if(basic2ChannelFuture.isSuccess()){
                log.info("HelloWorld! Netty server is running on port {}", basic2Port);
            }

            basic3ChannelFuture = basic3Bootstrap.bind(basic3Port).sync();
            if(basic3ChannelFuture.isSuccess()){
                log.info("HelloWorld! Netty server is running on port {}", basic3Port);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.close();
            System.exit(0);
        }
    }
}
