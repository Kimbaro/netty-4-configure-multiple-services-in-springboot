package com.template.netty.dto.netty;

import lombok.Data;

@Data
public class RequestPayload {

    private String reqFullTextMessage = "";
    private String reqHeaderTextMessage = "";
    private String reqBodyTextMessage = "";
    private long initTime = System.currentTimeMillis();

}
