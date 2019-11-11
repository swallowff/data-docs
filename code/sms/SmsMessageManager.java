package cn.huapu.power.server.manager.sms;

/**
 * @Description
 * @author shenyu
 * @create 2019-11-11
 */
public interface SmsMessageManager {

    String send(String phone,long expiredSeconds);

    boolean checkCode(String phone,String code);


}
