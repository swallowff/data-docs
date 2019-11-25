package cn.huapu.power.server.util;

import cn.huapu.power.core.cons.StatusCode;
import cn.huapu.power.core.vo.Ret;
import cn.huapu.power.server.user.bean.SysUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.rest.webmvc.support.RepositoryConstraintViolationExceptionMessage;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shenyu
 * @create 2019-11-5
 */
public class SpringValidatorUtil{
    private static Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();

    public static boolean validate(BindingResult bindingResult, Ret ret){
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errors) {
                sb.append(error.getDefaultMessage());
                sb.append(";");
            }
            ret.put(StatusCode.ERROR_REQUEST_PARAM);
            ret.setData(sb.toString());
            return false;
        } else {
            ret.put(StatusCode.SUCCESS);
            return true;
        }
    }

    public static <T> ValidationResult validateEntity(T obj){
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        return processResult(set);
    }

    public static <T> ValidationResult validateEntity(T obj, Class<?>... groups){
        Set<ConstraintViolation<T>> set = validator.validate(obj, groups);
        return processResult(set);
    }

    public static <T> ValidationResult validateProperty(T obj, String propertyName){
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName,Default.class);
        return processResult(set);
    }

    private static <T> ValidationResult processResult(Set<ConstraintViolation<T>> set ){
        ValidationResult result = new ValidationResult();
        if( CollectionUtils.isNotEmpty(set) ){
            result.setHasErrors(true);
            Map<String,String> errorMsg = new HashMap<String,String>();
            StringBuilder sb = new StringBuilder();
            for(ConstraintViolation<T> cv : set){
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
                sb.append(cv.getMessage()).append(";");
            }
            result.setErrorMsgMap(errorMsg);
            String str = sb.toString();
            result.setErrorMsg(str.substring(0,str.length() - 1));
        }
        return result;
    }



}
