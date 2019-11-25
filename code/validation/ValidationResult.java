package cn.huapu.power.server.util;

import java.util.Map;

/**
 * @Author: shenyu
 * @Date: 2019-11-11 10:26
 * @Description:
 */
public class ValidationResult {
    private boolean hasErrors;
    private Map<String, String> errorMsgMap;
    private String errorMsg;

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
