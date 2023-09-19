package com.bluechip.bcworkshinhanmessageq.modules.netty.handler.inbound;

import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaRepo.ShinhanMembersEntityCreateRepository;
import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaRepo.ShinhanMembersEntityRepository;
import com.bluechip.bcworkshinhanmessageq.service.proc.Biz_03001;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 메시지에 대한 비지니스로직 수행
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class MessageServiceHandler extends ChannelInboundHandlerAdapter {
    public static int count = 0;

    @Autowired
    @Qualifier("fixed_response_thread_pool_executor")
    private ThreadPoolExecutor fixedResponseThreadPoolExecutor;

    @Autowired
    @Qualifier("fixed_thread_pool")
    private ExecutorService executorService;

    @Autowired
    private ShinhanMembersEntityRepository shinhanMembersEntityRepository;

    @Autowired
    private ShinhanMembersEntityCreateRepository shinhanMembersEntityCreateRepository;


    @Value("${bc.kafka.brokers}")
    public String brokers;

    private String buff;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg != null) {
            /*
             *
             * 여기서 이벤트 수행
             *
             * */
            String receiveMsg = msg.toString();
            log.info("EVENT->channelRead:{}", receiveMsg);
            String bizCode = receiveMsg.substring(0, 5);
            String fullTextLength = receiveMsg.substring(5, 11);
            String companyId = receiveMsg.substring(11, 13);


            log.info("check -> {}", bizCode);
            if ("03001".contains(bizCode)) {
                String bodyText = receiveMsg.substring(32, receiveMsg.length());
                log.info("channelReadComplete slice -> [{}]", bodyText);

                Biz_03001 biz_03001 = new Biz_03001();
                biz_03001.execute(companyId, shinhanMembersEntityRepository, shinhanMembersEntityCreateRepository, bodyText);
//                String topic = "topic_03001";
//                new KafkaMain(topic, brokers, bodyText,shinhanMembersEntityRepository).run();

//                CompletableFuture<Boolean> blockThread = CompletableFuture.supplyAsync(() -> {
//                    log.info("BLUECHIP kafka publish 시작 {}", Thread.currentThread().getName());
//                    long beforeTime = System.currentTimeMillis();
////                    new KafkaMain(topic, brokers, bodyText).run();
////                구성
//                    long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
//                    long secDiffTime = (afterTime - beforeTime) / 1000; //두 시간에 차 계산
//                    log.info("BLUECHIP kafka publish 완료 {}s 경과", secDiffTime);
//                    return true;
//                });
//                try {
//                    blockThread.get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } finally {
//                    ctx.fireChannelReadComplete();
//                }
//            fixedResponseThreadPoolExecutor.execute(new KafkaMain(topic, brokers, bodyText));
            } else if ("77777".contains(bizCode)) {
                log.info("test if");
            }
            buff = msg.toString();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete -> {} Bytes", buff.getBytes().length);
        response(ctx, "finished");
        super.channelReadComplete(ctx);
    }

    public void response(ChannelHandlerContext ctx, String resMsg) {
        log.info("response");
        byte[] btsCheck = resMsg.getBytes();
        ByteBuf buf = ctx.alloc().buffer(btsCheck.length);
        buf.writeBytes(btsCheck);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("EVENT| handlerRemoved [end]");
        try {
            if (!buff.isEmpty() || !buff.isBlank()) {
                buff = null;
            }
        } catch (NullPointerException e) {
            log.info("EVENT| handlerRemoved [buff empty success]");
        } finally {
            buff = null;
        }
    }
}
