package com.bluechip.bcworkshinhanmessageq.service.kafka;

import com.bluechip.bcworkshinhanmessageq.util.HexUtil;
import com.bluechip.bcworkshinhanmessageq.util.SEED_KISA;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class ShinhanMembersDecode {
    private String encMsg;
    private String plainUserInfoText;
    private int maxSliceLength = 64;
    private StringBuffer fullTextData = new StringBuffer();

    private int count = 1;
    private final byte[] KEY_DEV = {(byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF,
            (byte) 0xFE, (byte) 0xDC, (byte) 0xBA, (byte) 0x98, (byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10};

    public String getFullTextData() throws Exception {
        return fullTextData.toString();
    }

    public void decode(String encMsg) {
        log.info("dykim ---> {}",encMsg.length());
        if (encMsg.length() % 64 == 0) {
            String sliceencMsg = encMsg.substring(0, maxSliceLength);
            plainUserInfoText = decSeed128hex(sliceencMsg);
            fullTextData.append(plainUserInfoText + "\n");
            String totalencMsg = encMsg.substring(maxSliceLength, encMsg.length());
            if (encMsg.length() != 64) {
                count++;
                decode(totalencMsg);
            } else {
                log.info("decode success -> {}", count);
            }
        } else {
            log.info("암호 전문 길이가 맞지 않습니다.");
            throw new RuntimeException("암호 전문길이가 맞지 않습니다.");
        }
    }

    public String decSeed128hex(String encMsg) {
        int pdwRoundKey[] = new int[32];
        SEED_KISA.SeedRoundKey(pdwRoundKey, KEY_DEV);
        byte[] btsDest = HexUtil.stringToHex(encMsg);
        byte pbPlain16[] = new byte[16];
        byte btsDest16[] = new byte[16];
        byte pbPlain[] = new byte[160];
        for (int i = 0; i < btsDest.length; i += 16) {
            btsDest16 = new byte[16];
            pbPlain16 = new byte[16];
            System.arraycopy(btsDest, i, btsDest16, 0, btsDest16.length);
            SEED_KISA.SeedDecrypt(btsDest16, pdwRoundKey, pbPlain16);
            System.arraycopy(pbPlain16, 0, pbPlain, i, btsDest16.length);
        }
        String decStrBody = new String(pbPlain, Charset.forName("UTF-8")).substring(0, 32);
        log.info("decSeed128hex -> {}", decStrBody);
        return decStrBody;
//        System.out.println(decStrBody);
    }
}
