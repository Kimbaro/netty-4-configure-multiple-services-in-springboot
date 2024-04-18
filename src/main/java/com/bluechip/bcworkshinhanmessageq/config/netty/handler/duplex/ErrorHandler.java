package com.bluechip.bcworkshinhanmessageq.config.netty.handler.duplex;

import com.bluechip.bcworkshinhanmessageq.config.PayloadRole;
import com.bluechip.bcworkshinhanmessageq.dto.netty.ResponseErrorPayload;
import com.bluechip.bcworkshinhanmessageq.utils.BasicUtils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;


/**에러 이벤트 처리부*/
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ErrorHandler extends ChannelDuplexHandler {
    // Message Decoding 과정에서 에러가 발생할 경우 이벤트 핸들러
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        log.info("EVENT| exceptionCaught [{}]",cause.getMessage());
//        cause.printStackTrace();
        ResponseErrorPayload errorPayload = new ResponseErrorPayload();
        if(cause.getMessage().contains("NumberFormatException")){
            String sampleHeader = BasicUtils.rightSpacePadding("111 222 333", PayloadRole.BASIC_HEADER_LEN.getValue(), Charset.defaultCharset());
            String sampleBody =  BasicUtils.rightSpacePadding(cause.getMessage(), PayloadRole.BASIC_ERROR_BODY_LEN.getValue(), Charset.defaultCharset());
            errorPayload.setResHeaderTextMessage(sampleHeader);
            errorPayload.setResBodyTextMessage(sampleBody);
        }else {
            String sampleHeader = BasicUtils.rightSpacePadding("111 222 333", PayloadRole.BASIC_HEADER_LEN.getValue(), Charset.defaultCharset());
            String sampleBody =  BasicUtils.rightSpacePadding(new UnknownError().getMessage(), PayloadRole.BASIC_ERROR_BODY_LEN.getValue(), Charset.defaultCharset());
            errorPayload.setResHeaderTextMessage(sampleHeader);
            errorPayload.setResBodyTextMessage(sampleBody);
        }
        ctx.writeAndFlush(errorPayload.getResHeaderTextMessage()+errorPayload.getResBodyTextMessage());
    }
}