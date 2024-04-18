package com.bluechip.bcworkshinhanmessageq.dto.netty;

import lombok.Data;

@Data
public class ResponseErrorPayload {
    private String resFullTextMessage = "";
    private String resHeaderTextMessage = "";
    private String resBodyTextMessage = "";
    private long initTime;
}
