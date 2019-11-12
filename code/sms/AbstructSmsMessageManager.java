package cn.huapu.power.server.manager.sms;

import cn.huapu.power.server.util.HttpRequestUtil;
import org.springframework.util.Assert;

/**
 * @author shenyu
 * @Description smsMessageManager抽象类
 * @create 2019-11-11
 */
public abstract class AbstructSmsMessageManager implements SmsMessageManager{
    /**
     *  短信10分钟限流次数
     */
    private static int LIMIT_TIMES = 10;

    @Override
    public String send(String phone,long expiredSeconds) throws SmsMessageException{
        MessageCodeDao messageCodeDao = this.getMessageCodeDao();
        String remoteIp = HttpRequestUtil.getIp();
        long sendCount = messageCodeDao.sendTimesForIp(remoteIp);
        if (sendCount > LIMIT_TIMES){
            throw new SmsMessageException("您发送的验证码次数过多,请于"+messageCodeDao.getLimitSecondsRemaining(remoteIp)+"秒后再试");
        }
        String code = this.genCode();
        this.sendMessage(phone,code);
        messageCodeDao.saveCode(phone,code,expiredSeconds);
        return code;
    }

    @Override
    public boolean validateCode(String phone, String code) {
        Assert.notNull(code,"code can not be null");
        return code.equals(this.getMessageCodeDao().getAndDelCodeByPhone(phone));
    }

    /**
     * @Description: 客户端自定义消息发送
     */
    protected abstract void sendMessage(String phone, String code) throws SmsMessageException;

    /**
     * @Description: 生成验证码
     */
    protected abstract String genCode();

    /**
     * @Description: 获取客户端自定义的验证码持久层dao
     */
    protected abstract MessageCodeDao getMessageCodeDao();



}
