package com.template.netty.dto.netty;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestPayload {

    private final UUID UUID_OBJ = UUID.randomUUID();
    private String flag = null;
    private String reqFullTextMessage = "";
    private String reqHeaderTextMessage = "";
    private String reqBodyTextMessage = "";
    private ChannelHandlerContext sessionHandlerContext = null;
    private long initTime = System.currentTimeMillis();
}
