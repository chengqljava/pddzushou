package com.cheng.helper;

import org.springside.modules.utils.security.CryptoUtil;
import org.springside.modules.utils.text.EncodeUtil;

import com.cheng.helper.domain.UserDO;

public class Test {
    public static void main(String[] args) {
        UserDO userDO = new UserDO();
        if (userDO.getPassword() == null) {
            userDO.setPassword("QQ18638579289");
        }
        userDO.setSalt(EncodeUtil.encodeHex(CryptoUtil.generateHmacSha1Key()));
        userDO.setPassword(EncodeUtil.encodeHex(
            CryptoUtil.hmacSha1(userDO.getPassword().getBytes(), userDO.getSalt().getBytes())));
        System.out.println("salt:" + userDO.getSalt() + "\npassword:" + userDO.getPassword());
    }
}
