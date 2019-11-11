package cn.huapu.power.server.manager.sms;

import org.springframework.util.Assert;

/**
 * @author shenyu
 * @Description smsMessageManager抽象类
 * @create 2019-11-11
 */
public abstract class AbstructSmsMessageManager implements SmsMessageManager{

    @Override
    public String send(String phone,long expiredSeconds) throws SmsMessageException{
        String code = this.genCode();
        sendMessage(phone,code);
        MessageCodeDao messageCodeDao = getMessageCodeDao();
        messageCodeDao.saveCode(phone,code,expiredSeconds);
        return code;
    }

    @Override
    public boolean checkCode(String phone, String code) {
        Assert.notNull(code,"code can not be null");
        return code.equals(this.getMessageCodeDao().getAndDelCodeByPhone(phone));
    }

    /**
     * @Author: shenyu
     * @Date: 2019-11-11 17:08
     * @Description: 客户端自定义消息发送
     */
    protected abstract void sendMessage(String phone, String code) throws SmsMessageException;

    /**
     * @Author: shenyu
     * @Date: 2019-11-11 17:07
     * @Description: 生成验证码
     */
    protected abstract String genCode();

    /**
     * @Author: shenyu
     * @Date: 2019-11-11 17:07
     * @Description: 获取客户端自定义的验证码持久层dao
     */
    protected abstract MessageCodeDao getMessageCodeDao();


}
