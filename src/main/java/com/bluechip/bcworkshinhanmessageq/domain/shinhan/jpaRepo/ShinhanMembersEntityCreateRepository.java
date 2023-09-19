package com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaRepo;

import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaEntity.TB_Zone_Member_CreateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("primaryTransactionManager")
public interface ShinhanMembersEntityCreateRepository extends JpaRepository<TB_Zone_Member_CreateEntity, Integer> {
    public TB_Zone_Member_CreateEntity findByHpNoAndCompanyId(String hpNo, String companyId);
}
