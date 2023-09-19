package com.bluechip.bcworkshinhanmessageq.service.kafka;

import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaRepo.ShinhanMembersEntityRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaMain {
    private String encMsg;
    private String topic;
    private String brokers;

    private ShinhanMembersEntityRepository shinhanMembersEntityRepository;

    public KafkaMain(String topic, String brokers, String encMsg, ShinhanMembersEntityRepository shinhanMembersEntityRepository) {
        this.topic = topic;
        this.encMsg = encMsg;
        this.brokers = brokers;
        this.shinhanMembersEntityRepository = shinhanMembersEntityRepository;
    }

//    public void run() {
//        String[] brokerArrays = brokers.split(",");
//        KafkaProducer<String, String> producer = null;
//        for (String broker_ip : brokerArrays) {
////            Map mapof = new HashMap();
////            mapof.put("acks", "-1");
////            mapof.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
////            mapof.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
////            mapof.put("bootstrap.servers", "broker_ip.trim()");
//
//
//            producer = new KafkaProducer<>(Map.of(
//                    "acks", "-1",
//                    "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
//                    "value.serializer", "org.apache.kafka.common.serialization.StringSerializer",
//                    "bootstrap.servers", broker_ip.trim()
//            ));
//            /*토픽별 분기처리, DB 손보의 경우를 대비하자.*/
//            if ("topic_03001".equals(this.topic)) {
//                /*헤더구성*/
//                String businessCode = "00001".substring(0, 5);
//                String companyCode = String.format("%-20s", "20001").substring(0, 10);
//                String topic = String.format("%-20s", "topic_03001").substring(0, 20);
//                String group = String.format("%-20s", "group_10001").substring(0, 20);
//                String request_date = String.format("%-20s", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).substring(0, 20);
//                String headerPayload = businessCode + companyCode + topic + group + request_date;
//                String[] decodeArray = null;
//                StringBuffer fullTextData = new StringBuffer();
//                try {
//                    ShinhanMembersDecode membersDecode = new ShinhanMembersDecode();
//                    membersDecode.decode(encMsg, fullTextData);
//                    if (fullTextData != null || !fullTextData.toString().equals("")) {
//                        decodeArray = fullTextData.toString().split("\n");
//                        HashMap<Integer, String> bodyMap = new HashMap<Integer, String>();
////                        KafkaProducerService kafkaProducerService = new KafkaProducerService(topic, brokers, encMsg);
//                        for (String member : decodeArray) {
//                            log.info(member);
////                            String body = member.substring(member.indexOf("#") + 1, member.indexOf("$"));
////                            bodyMap.put(1, ParsingManager.subByteString(body, 0, 12, Charset.forName("UTF-8")));
////                            bodyMap.put(2, ParsingManager.subByteString(body, 12, 13, Charset.forName("UTF-8")));
////                            bodyMap.put(3, ParsingManager.subByteString(body, 13, 14, Charset.forName("UTF-8")));
////                            bodyMap.put(4, ParsingManager.subByteString(body, 14, 15, Charset.forName("UTF-8")));
////                            log.info("[{}] [{}]", bodyMap);
//                            /**/
//                            //카프카전송 -> 목적지맞지 않아 주석하였음.
//                            //kafkaProducerService.onApplicationEvent(headerPayload + member, producer);
//                            /**/
//                            /*가상번호 생성====================================START*/
////                            int len = 11;
////                            ThreadLocalRandom random = ThreadLocalRandom.current();
////                            String result = "";
////
////                            for (int i = 0; i < len / 10; i++) {
////                                result += random.nextLong(1_000_000_000L, 10_000_000_000L);
////                            }
////
////                            if (result.length() != len) {
////                                if (result.length() < len) {
////                                    result += random.nextLong(1_000_000_000L, 10_000_000_000L);
////                                }
////                                result = result.substring(0, len);
////                            }
//                            /*가상번호 생성==================================== END*/
//                            ShinhanMembersEntity memberEntity = shinhanMembersEntityRepository.findByHpNoAndCompanyId("81258049135", "40");
//                            log.info("search1 ->[{}]", memberEntity);
//                            if (memberEntity == null) {
//                                memberEntity = ShinhanMembersEntity.builder().flag(1).hpNo("81258049135").companyId("40").withdrawalYn("Y").agreeYn("Y").tradeYn("Y").createDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
//                            } else {
//                                memberEntity.setWithdrawalYn("N");
//                                memberEntity.setAgreeYn("N");
//                                memberEntity.setTradeYn("N");
//                                memberEntity.setUpdateDate(LocalDateTime.now());
//                            }
//                            log.info("search2 ->[{}]", memberEntity);
//
//                            shinhanMembersEntityRepository.save(memberEntity);
////
////
////                            1,
////                                    result,
////                                    "bluechip12#blueuser_encKey!@#$52", "bluechip12!@#$52", "aes", "base64",
////                                    "10001",
////                                    "Y",
////                                    "Y",
////                                    "Y",
////                                    LocalDateTime.now(), LocalDateTime.now());
//                            break;
//                        }
//                    } else {
//                        Thread.currentThread().interrupt();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    switch (e.getMessage().contains("TimeoutException") + "") {
//                        case "true":
//                            log.info("===============Disconnected: Connection Attempt Failed [{}]", broker_ip);
//                            log.info("===============Retry: (ว˙∇˙)ง(ง˙∇˙)ว");
//                            continue;
//                        case "false":
//                            /*정상종료*/
//                            log.info("case \"false\"");
//                            break;
//                    }
//                } finally {
//                    log.info("Finished dispatcher demo - Closing Kafka Producer.");
//                    producer.flush();
//                    producer.close();
//                }
//            }
//            break;
//        }
//    }
}
