package cn.huapu.power.server.manager.sms;

/**
 * @Description
 * @author shenyu
 * @create 2019-11-11
 */
public interface MessageCodeDao {

    void saveCode(String phone, String code, long expiredSeconds);

    String getAndDelCodeByPhone(String phone);

}
