package cn.huapu.power.server.manager.sms;

/**
 * @author shenyu
 * @Description smsMessageManager抽象类
 * @create 2019-11-11
 */
public abstract class AbstructSmsMessageManager implements SmsMessageManager{

    @Override
    public void send(String phone) {
        String code = this.genCode();
        try {
            sendMessage(phone,code);
            MessageCodeDao messageCodeDao = getMessageCodeDao();
            messageCodeDao.saveCode(phone,code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void sendMessage(String phone,String code);

    protected abstract String genCode();

    protected abstract MessageCodeDao getMessageCodeDao();

}
