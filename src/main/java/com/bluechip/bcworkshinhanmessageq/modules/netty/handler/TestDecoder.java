package com.bluechip.bcworkshinhanmessageq.modules.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 메시지검증필요시 이벤트 처리부
 */
@Component
@RequiredArgsConstructor
public class TestDecoder extends ByteToMessageDecoder {
    private int DATA_LENGTH = 20000;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < DATA_LENGTH) {
            return;
        }
        out.add(in.readBytes(DATA_LENGTH));
    }
}