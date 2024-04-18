package com.bluechip.bcworkshinhanmessageq.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayloadRole {
    BASIC_HEADER_LEN(32),
    BASIC_ERROR_BODY_LEN(100);

    private final int value;
}
