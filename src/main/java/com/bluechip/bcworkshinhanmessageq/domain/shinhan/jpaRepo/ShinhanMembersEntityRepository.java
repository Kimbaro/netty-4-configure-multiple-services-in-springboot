package com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaRepo;

import com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaEntity.TB_Zone_Member_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("primaryTransactionManager")
public interface ShinhanMembersEntityRepository extends JpaRepository<TB_Zone_Member_Entity, Integer> {
}
