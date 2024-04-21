package com.template.netty.config.netty.handler.decode;

import com.template.netty.dto.netty.RequestPayload;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 요청 페이로드 파싱 후 모든 데이터 수신처리
 */
@Slf4j
public class ServerDetailDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        /*헤더자르기*/
        int iReadableBytes = in.readableBytes();

        if (iReadableBytes < 32) {
            return;
        }
        in.markReaderIndex();

        byte[] headerBuffer = new byte[32];
        in.readBytes(headerBuffer);
        if (headerBuffer.length < 32) {
            log.info("NETTY-EVENT->payload header length mismatch | [{} < 32]", headerBuffer.length);
            ctx.close();
            return;
        }
        String headerText = new String(headerBuffer);

        in.resetReaderIndex();
        String flag = headerText.substring(0, 5).trim();
        Integer bodyLength = Integer.parseInt(headerText.substring(5, 11).trim());
        iReadableBytes = in.readableBytes();
        if (iReadableBytes < bodyLength) {
            log.info("2. iReadableBytes < iObjectLength return: iReadableBytes:{}, iBodylength:{}", iReadableBytes, bodyLength);
            return;
        }

        /*바디자르기*/
        byte[] bodyBuffer = new byte[bodyLength];
        in.readBytes(bodyBuffer);
//                log.info("-----> {} {}", bodyLength, body); -> 값 체크
        if (iReadableBytes < bodyLength) {
            log.info("NETTY-EVENT->payload body length mismatch | [{} < {}]", bodyBuffer.length, bodyLength);
            return;
        }

        RequestPayload requestVO = new RequestPayload();
        requestVO.setFlag(flag.toString());
        requestVO.setReqHeaderTextMessage(new String(headerBuffer));
        requestVO.setReqBodyTextMessage(new String(bodyBuffer));
        requestVO.setSessionHandlerContext(ctx);

        log.info("input data -----> [{}]", requestVO);
        out.add(requestVO);
    }
}