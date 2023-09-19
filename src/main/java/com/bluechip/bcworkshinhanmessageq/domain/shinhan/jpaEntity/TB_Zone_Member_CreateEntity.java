package com.bluechip.bcworkshinhanmessageq.domain.shinhan.jpaEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@SequenceGenerator(
        name = "seq_tb_zone_member", //시퀀스 제너레이터 이름
        sequenceName = "seq_tb_zone_member", //시퀀스 이름
        initialValue = 1, //시작값
        allocationSize = 1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "tb_zone_member", schema = "public", catalog = "bcwork_shinhan")
@Slf4j
public class TB_Zone_Member_CreateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_zone_member")
    private long id;

    @ColumnTransformer(
            write = "set_flag_number(?::int)"
    )
    @Column(name = "flag")
    private int flag;

    @ColumnTransformer(
            read = "fun_bluechip_decode(hp_no,'bluechip12#blueuser_encKey!@#$52','bluechip12!@#$52','aes','base64')",
            write = "fun_bluechip_encode(?::bytea,'bluechip12#blueuser_encKey!@#$52', 'bluechip12!@#$52', 'aes','base64')"
    )
    @Column(name = "hp_no")
    private String hpNo;
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "withdrawal_yn")
    private String withdrawalYn;
    @Column(name = "agree_yn")
    private String agreeYn;
    @Column(name = "trade_yn")
    private String tradeYn;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public static void main(String[] args) {
        long id = 0;
        String temp = id + "";
        System.out.println(temp);
        int lastIndex = temp.length() - 1;
        long initFlag = Long.valueOf(temp.charAt(lastIndex) + "");
        System.out.println(initFlag);
    }
}
