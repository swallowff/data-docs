package cn.huapu.power.server.manager.sms;

import cn.huapu.power.core.cfg.cache.RedisUtil;
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

    public static final String MSG_CODE_KEY = "sms:messageCode";

    @Override
    public void saveCode(String phone, String code) {
        redisUtil.hset(MSG_CODE_KEY,phone,code);
    }

    @Override
    public String getCodeByPhone(String phone) {
        return (String) redisUtil.hget(MSG_CODE_KEY,phone);
    }
}
