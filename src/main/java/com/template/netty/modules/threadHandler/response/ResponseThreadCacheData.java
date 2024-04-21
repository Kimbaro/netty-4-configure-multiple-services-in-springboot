package com.template.netty.modules.threadHandler.response;

import com.template.netty.dto.netty.RequestPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.currentThread;
@Slf4j
@Component
public class ResponseThreadCacheData {

    private final HashMap<UUID, RequestPayload> cacheDataMap = new HashMap<UUID, RequestPayload>();
    private final List<RequestPayload> cacheDataList = new ArrayList<RequestPayload>();
    private final Object monitor = new Object();


    public void addResponsePayload(RequestPayload requestPayload) {

    }

    public void getResponsePayload() {

    }
}
