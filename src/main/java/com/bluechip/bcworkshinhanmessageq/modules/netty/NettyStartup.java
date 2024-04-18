package com.bluechip.bcworkshinhanmessageq.modules.netty;

import com.bluechip.bcworkshinhanmessageq.modules.netty.core.NettyServerSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NettyStartup implements ApplicationListener<ApplicationReadyEvent> {
    private final NettyServerSocket nettyServerSocket;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        nettyServerSocket.start();
    }
}
