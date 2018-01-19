package com.cheng.helper.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cheng.helper.ClientProperties;
import com.cheng.helper.domain.ShopDO;
import com.cheng.helper.service.ShopService;
import com.cheng.helper.utils.OAuthUtil;

/**
 * 定时任务
 * @author chengql
 * @version $Id: TimedTaskJob.java, v 0.1 2018年1月18日 下午7:14:17 chengql Exp $
 */
@Component(value = "timedTaskJob")
public class TimedTaskJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimedTaskJob.class);
    @Autowired
    private ShopService         shopService;
    @Autowired
    private ClientProperties    clientProperties;

    public void refreshTokenJob(String hours) {
        LOGGER.info("--------TimedTaskJob refreshTokenJob start--------");
        List<Integer> hourList = new ArrayList<Integer>();
        try {
            if (StringUtils.isNoneBlank(hours)) {
                for (String hourStr : hours.split(",")) {
                    hourList.add(Integer.parseInt(hourStr));
                }

            } else {
                hourList.add(7);
            }
        } catch (Exception e) {
            LOGGER.error("--------TimedTaskJob refreshTokenJob error--------" + e.getMessage());
        }
        List<ShopDO> shopList = null;
        List<ShopDO> batchUpdateShop = null;
        try {
            Date date = new Date();
            JSONObject jsonObject = null;
            if (!hourList.isEmpty()) {
                for (int hour : hourList) {
                    shopList = shopService.refreshTokenList(hour);
                    if (!shopList.isEmpty()) {
                        batchUpdateShop = new ArrayList<ShopDO>();
                        for (ShopDO shop : shopList) {
                            jsonObject = OAuthUtil.refreshToken(shop.getRefreshToken(),
                                clientProperties.getClientId(), clientProperties.getClientSecret());
                            if (jsonObject != null && !jsonObject.containsKey("error_response")) {
                                shop.setAccessToken(jsonObject.getString("access_token"));
                                shop.setRefreshToken(jsonObject.getString("refresh_token"));
                                shop.setTokenUpdateTime(date);
                                batchUpdateShop.add(shop);
                            } else {
                                shopService.updateCleanToken(shop.getId());
                            }

                        }
                        if (!batchUpdateShop.isEmpty()) {
                            shopService.batchUpdate(batchUpdateShop);
                        }
                    }

                }

            }
        } catch (Exception e) {
            LOGGER.error("--------TimedTaskJob refreshTokenJob error--------" + e.getMessage());
        }
        LOGGER.info("--------TimedTaskJob refreshTokenJob end--------");
    }

}
