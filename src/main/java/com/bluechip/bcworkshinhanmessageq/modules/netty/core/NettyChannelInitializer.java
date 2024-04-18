package com.bluechip.bcworkshinhanmessageq.modules.netty.core;

import com.bluechip.bcworkshinhanmessageq.modules.netty.handler.duplex.ErrorHandler;
import com.bluechip.bcworkshinhanmessageq.modules.netty.handler.duplex.SessionHandler;
import com.bluechip.bcworkshinhanmessageq.modules.netty.handler.inbound.MessageServiceHandler;
import com.bluechip.bcworkshinhanmessageq.modules.netty.handler.outbound.MessageWriteHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final ErrorHandler errorHandler;
    private final MessageWriteHandler messageWriteHandler;
    private final SessionHandler sessionHandler;

    private final MessageServiceHandler messageServiceHandler;

    // 클라이언트 소켓 채널이 생성될 때 호출
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

//        ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(20000)); //64배수
        // decoder는 @Sharable이 안 됨, Bean 객체 주입이 안 되고, 매번 새로운 객체 생성해야 함

        // 뒤이어 처리할 디코더 및 핸들러 추가
        /*inbound*/
//        pipeline.addLast(testDecoder);
//        pipeline.addLast(messageReadHandler);
//        pipeline.addLast(new TestDecoder());
        pipeline.addLast(new ByteToMessageDecoder() {
            @Override
            protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                /*헤더자르기*/
                int iReadableBytes = in.readableBytes();

                if (iReadableBytes < 32) {
                    return;
                }
                in.markReaderIndex();

                byte[] headerBuffer = new byte[32];
                in.readBytes(headerBuffer);
                if (headerBuffer.length < 32) {
                    log.info("EVENT->payload header length mismatch | [{} < 32]", headerBuffer.length);
                    ctx.close();
                    return;
                }
                String headerText = new String(headerBuffer);

                in.resetReaderIndex();
                Integer bodyLength = Integer.parseInt(headerText.substring(5, 11).trim());
                iReadableBytes = in.readableBytes();
                if (iReadableBytes < bodyLength) {
                    log.info("2. iReadableBytes < iObjectLength return: iReadableBytes:{}, iBodylength:{}", iReadableBytes, bodyLength);
                    return;
                }

                /*바디자르기*/
                byte[] bodyBuffer = new byte[bodyLength];
                in.readBytes(bodyBuffer);
                String inletPayload = new String(bodyBuffer);
//                log.info("-----> {} {}", bodyLength, body); -> 값 체크
                if (iReadableBytes < bodyLength) {
                    log.info("EVENT->payload body length mismatch | [{} < {}]", bodyBuffer.length, bodyLength);
                    return;
                }

                log.info("input data -----> [{}]", inletPayload);
                out.add(inletPayload);
            }
        });

        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

        /*duplex*/
        pipeline.addLast(sessionHandler);
        pipeline.addLast(errorHandler);

        /*inbound*/
        pipeline.addLast(messageServiceHandler);

        /*outbound*/
        pipeline.addLast(messageWriteHandler);
    }
}