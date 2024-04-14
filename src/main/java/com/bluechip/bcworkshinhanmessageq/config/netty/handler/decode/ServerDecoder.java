package com.bluechip.bcworkshinhanmessageq.config.netty.handler.decode;

import com.bluechip.bcworkshinhanmessageq.dto.netty.RequestPayload;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ServerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //input byte stream
        int iReadableBytes = in.readableBytes();
        byte[] btsMsg= new byte[iReadableBytes];
        in.readBytes(btsMsg);

        RequestPayload requestVO = new RequestPayload();
        requestVO.setReqFullTextMessage(new String(btsMsg));
        requestVO.setInitTime(System.currentTimeMillis());
        out.add(requestVO);
    }
}