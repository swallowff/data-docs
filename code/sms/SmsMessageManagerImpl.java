package cn.huapu.power.server.manager.sms;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shenyu
 * @Description
 * @create 2019-11-11
 */
@Component
public class SmsMessageManagerImpl extends AbstructSmsMessageManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int min = 100000;
    private static final int max = 999999;

    @Autowired
    private MessageCodeDao messageCodeDao;

    @Override
    public String genCode() {
        return String.valueOf(RandomUtils.nextInt(min,max));
    }

    @Override
    protected void sendMessage(String phone, String code) throws SmsMessageException{
//        AliyunSmsUtils.sendSms(phone,code);
        if (logger.isDebugEnabled()){
            logger.debug("发送验证码成功,phone={},code={}",phone,code);
        }
    }

    @Override
    protected MessageCodeDao getMessageCodeDao() {
        return this.messageCodeDao;
    }
}
