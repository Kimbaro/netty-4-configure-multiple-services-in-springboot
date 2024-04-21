package com.template.netty.dto.netty;

import io.netty.channel.ChannelHandlerContext;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ResponsePayload {
    private final UUID UUID_OBJ = UUID.randomUUID();
    private String resHeaderTextMessage = "";
    private String resBodyTextMessage = "";
    private String fullTextResTextMessage = "";
    private long initTime = System.currentTimeMillis();
    private ChannelHandlerContext sessionHandlerContext = null;
}
