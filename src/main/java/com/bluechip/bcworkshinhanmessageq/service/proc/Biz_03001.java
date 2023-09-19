package com.bluechip.bcworkshinhanmessageq.service.proc;

import com.bluechip.bcworkshinhanmessageq.config.ParsingManager;
import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaEntity.TB_Zone_Member_CreateEntity;
import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaEntity.TB_Zone_Member_Entity;
import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaRepo.ShinhanMembersEntityCreateRepository;
import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaRepo.ShinhanMembersEntityRepository;
import com.bluechip.bcworkshinhanmessageq.service.kafka.ShinhanMembersDecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
@Component
@Scope(value = "prototype")
public class Biz_03001 {
    public void execute(String companyId, ShinhanMembersEntityRepository shinhanMembersEntityRepository,
                        ShinhanMembersEntityCreateRepository shinhanMembersEntityCreateRepository,
                        String encMsg) {
        ShinhanMembersDecode membersDecode = new ShinhanMembersDecode();
        try {
            membersDecode.decode(encMsg); //전문 복호화 및 정형화
            String[] decodeArray = membersDecode.getFullTextData().split("\\n");
            HashMap<Integer, String> bodyMap = new HashMap<Integer, String>();
            for (String member : decodeArray) { // 고객정보 한명씩 뽑아냄
                log.info(member);
                String body = member.substring(member.indexOf("#") + 1, member.indexOf("$"));
                bodyMap.put(1, ParsingManager.subByteString(body, 0, 12, Charset.forName("UTF-8")));
                bodyMap.put(2, ParsingManager.subByteString(body, 12, 13, Charset.forName("UTF-8")));
                bodyMap.put(3, ParsingManager.subByteString(body, 13, 14, Charset.forName("UTF-8")));
                bodyMap.put(4, ParsingManager.subByteString(body, 14, 15, Charset.forName("UTF-8")));
                String hpNo = bodyMap.get(1);
                if(!"010".equals(hpNo.substring(0,3))){
                    throw new RuntimeException("번호형식이 맞지 않음.");
                }
                String withdrawalYn = bodyMap.get(2);
                String agreeYn = bodyMap.get(3);
                String tradeYn = bodyMap.get(4);

                /*고객정보 DB transaction insert, update 수행*/
                TB_Zone_Member_CreateEntity memberEntity = shinhanMembersEntityCreateRepository.findByHpNoAndCompanyId(hpNo, companyId);
                log.info("search1 ->[{}]", memberEntity);
                if (memberEntity == null) {
                    memberEntity = TB_Zone_Member_CreateEntity.builder().hpNo(hpNo).companyId(companyId).withdrawalYn(withdrawalYn).agreeYn(agreeYn).tradeYn(tradeYn).createDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
                    shinhanMembersEntityCreateRepository.save(memberEntity);
                } else {
                    TB_Zone_Member_Entity TBZoneMemberEntity = TB_Zone_Member_Entity.builder()
                            .id(memberEntity.getId())
                            .flag(memberEntity.getFlag())
                            .hpNo(memberEntity.getHpNo())
                            .companyId(memberEntity.getCompanyId())
                            .withdrawalYn(withdrawalYn)
                            .agreeYn(agreeYn)
                            .tradeYn(tradeYn)
                            .createDate(memberEntity.getCreateDate())
                            .updateDate(LocalDateTime.now())
                            .build();
                    shinhanMembersEntityRepository.save(TBZoneMemberEntity);
//                    memberEntity.setWithdrawalYn(withdrawalYn);
//                    memberEntity.setAgreeYn(agreeYn);
//                    memberEntity.setTradeYn(tradeYn);
//                    memberEntity.setUpdateDate(LocalDateTime.now());
                    log.info("search2 ->[{}]", TBZoneMemberEntity);
                }

//                shinhanMembersEntityRepository.save(memberEntity);
//                log.info("[{}]", bodyMap);
            }
        } catch (Exception e) {
            log.error("전문 처리중 오류 발생",e.getCause());
        }
    }
}
