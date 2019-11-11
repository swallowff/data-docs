package cn.huapu.power.server.manager.sms;

/**
 * @author shenyu
 * @Description
 * @create 2019-11-11
 */
public class SmsMessageException extends RuntimeException {
    public SmsMessageException() {
    }

    public SmsMessageException(String message) {
        super(message);
    }

    public SmsMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsMessageException(Throwable cause) {
        super(cause);
    }
}
