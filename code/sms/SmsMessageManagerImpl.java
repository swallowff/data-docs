package cn.huapu.power.server.manager.sms;

import cn.huapu.power.server.util.AliyunSmsUtils;
import com.aliyuncs.exceptions.ClientException;
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

    private static final int min = 100000;
    private static final int max = 999999;

    @Override
    public String genCode() {
        return String.valueOf(RandomUtils.nextInt(min,max));
    }

    @Override
    protected void sendMessage(String phone, String code) throws SmsMessageException{
        AliyunSmsUtils.sendSms(phone,code);
        System.out.println("成功发送一条短信:phone="+phone+";code="+code);
    }

    @Override
    protected MessageCodeDao getMessageCodeDao() {
        return this.messageCodeDao;
    }
}
