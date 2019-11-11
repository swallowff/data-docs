package cn.huapu.power.server.manager.sms;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shenyu
 * @Description
 * @create 2019-11-11
 */
@Component
public class SmsMessageManagerImpl extends AbstructSmsMessageManager {
    @Autowired
    private MessageCodeDao messageCodeDao;

    @Override
    public void send(String phone) {
        super.send(phone);
    }

    @Override
    public String genCode() {
        return String.valueOf(RandomUtils.nextInt(100000,999999));
    }

    @Override
    protected void sendMessage(String phone, String code) {
        System.out.println("成功发送一条短信:phone="+phone+";code="+code);
    }

    @Override
    protected MessageCodeDao getMessageCodeDao() {
        return this.messageCodeDao;
    }
}
