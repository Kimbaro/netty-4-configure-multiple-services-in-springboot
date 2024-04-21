package com.template.netty.modules.applicationStartModules;

import com.template.netty.config.netty.channel.basic.NettyBasicChannelInitializer;
import com.template.netty.config.netty.channel.basic2.NettyBasic2ChannelInitializer;
import com.template.netty.modules.threadHandler.request.RequestThreadCacheData;
import com.template.netty.modules.threadHandler.response.ResponseThreadCacheData;
import com.template.netty.modules.threadHandler.service.ServiceThreadCacheData;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * NettyFramework Start
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class NettyStartupModule implements ApplicationListener<ApplicationReadyEvent> {

    @Qualifier(value = "basicBootstrap")
    private final ServerBootstrap basicBootstrap;

    @Qualifier(value = "basic2Bootstrap")
    private final ServerBootstrap basic2Bootstrap;

    @Value("${server-setup.port.basic}")
    private int basicPort;

    @Value("${server-setup.port.basic2}")
    private int basic2Port;

    private final RequestThreadCacheData requestThreadCacheData;
    private final ResponseThreadCacheData responseThreadCacheData;
    private final ServiceThreadCacheData serviceThreadCacheData;
    private final ConfigurableApplicationContext context;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("### NettyStartupModule ###");
        /*Netty Server Start Sequence*/
        ChannelFuture basicChannelFuture = null;
        ChannelFuture basic2ChannelFuture = null;
        try {
            this.basicBootstrap.childHandler(new NettyBasicChannelInitializer(requestThreadCacheData, responseThreadCacheData, serviceThreadCacheData));
            basicChannelFuture = this.basicBootstrap.bind(basicPort).sync();
            if (basicChannelFuture.isSuccess()) {
                log.info("HelloWorld! Netty server is running on port {}", basicPort);
            }
            this.basic2Bootstrap.childHandler(new NettyBasic2ChannelInitializer(requestThreadCacheData, responseThreadCacheData, serviceThreadCacheData));
            basic2ChannelFuture = this.basic2Bootstrap.bind(basic2Port).sync();
            if (basic2ChannelFuture.isSuccess()) {
                log.info("HelloWorld! Netty server is running on port {}", basic2Port);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.close();
            System.exit(0);
        }
    }
}
