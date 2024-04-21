package com.template.netty.model;

import com.template.netty.dto.netty.RequestPayload;

public interface ModelTemplate {
    public void execute(RequestPayload requestPayload);
}
