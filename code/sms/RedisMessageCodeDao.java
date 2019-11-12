package cn.huapu.power.server.manager.sms;

import cn.huapu.power.core.cfg.cache.RedisUtil;
import cn.huapu.power.server.util.HttpRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shenyu
 * @Description
 * @create 2019-11-11
 */
@Component
public class RedisMessageCodeDao implements MessageCodeDao {
    @Autowired
    private RedisUtil redisUtil;

    public static final String MSG_CODE_KEY = "sms:phone:code:";
    public static final String IP_SEND_LOG_KEY = "sms:ip:sendlog:";

    @Override
    public void saveCode(String phone, String code,long expiredSeconds) {
        redisUtil.set(MSG_CODE_KEY+phone,code,expiredSeconds);
        String ipCountKey = IP_SEND_LOG_KEY+ HttpRequestUtil.getIp();
        redisUtil.incre(ipCountKey);
        redisUtil.expire(ipCountKey,10*60);
    }

    @Override
    public String getAndDelCodeByPhone(String phone) {
        String key = MSG_CODE_KEY+phone;
        String code = redisUtil.get(key);
        if (StringUtils.isNotBlank(code)){
            redisUtil.delete(key);
        }
        return code;
    }

    @Override
    public long sendTimesForIp(String ip) {
        String ipCountKey = IP_SEND_LOG_KEY+ HttpRequestUtil.getIp();
        String val = redisUtil.get(ipCountKey);
        long count = 0;
        if (StringUtils.isNotBlank(val)){
            count = Long.valueOf(val);
        }
        return count;
    }

    @Override
    public long getLimitSecondsRemaining(String ip) {
        return redisUtil.getExpiredSeconds(IP_SEND_LOG_KEY+ip);
    }
}
