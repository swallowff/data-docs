package cn.huapu.power.server.manager.sms;

import cn.huapu.power.core.cfg.cache.RedisUtil;
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

    public static final String MSG_CODE_KEY = "sms:messageCode:";

    @Override
    public void saveCode(String phone, String code,long expiredSeconds) {
        redisUtil.set(MSG_CODE_KEY+phone,code,expiredSeconds);
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
}
