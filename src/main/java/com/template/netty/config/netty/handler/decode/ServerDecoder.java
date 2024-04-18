package com.template.netty.config.netty.handler.decode;

import com.template.netty.dto.netty.RequestPayload;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 요청 페이로드 파싱없이 모든 데이터 수신처리
 * */
public class ServerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //input byte stream
        int iReadableBytes = in.readableBytes();
        byte[] btsMsg= new byte[iReadableBytes];
        in.readBytes(btsMsg);

        RequestPayload requestVO = new RequestPayload();
        requestVO.setReqFullTextMessage(new String(btsMsg));
        out.add(requestVO);
    }
}