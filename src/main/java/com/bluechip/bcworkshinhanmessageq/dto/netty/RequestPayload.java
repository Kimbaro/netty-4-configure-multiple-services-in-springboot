package com.bluechip.bcworkshinhanmessageq.dto.netty;

import lombok.Data;

@Data
public class RequestPayload {

    private String reqFullTextMessage = "";
    private long initTime;

}
