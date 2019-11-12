package cn.huapu.power.core.cfg.weblog;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @Author: shenyu
 * @Date: 2019-11-12 17:06
 * @Description: 接口日志处理切面
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<>();


    @Pointcut("execution(public * cn.huapu.power..*.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("请求URL : " + request.getRequestURL().toString());
        logger.info("请求HTTP_METHOD : " + request.getMethod());
        logger.info("请求IP : " + request.getRemoteAddr());
        logger.info("请求CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        String contentType = request.getContentType();
        if (null != contentType){
            if (contentType.equals(MediaType.APPLICATION_JSON_VALUE) || contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE)){
                logger.info("请求参数值 : " + JSONObject.toJSONString(joinPoint.getArgs()));
            }else if (contentType.equals(MediaType.APPLICATION_XML_VALUE)){
                logger.info("请求参数值 : " + JSONObject.toJSONString(joinPoint.getArgs()));
            }else if (contentType.equals(MediaType.TEXT_PLAIN_VALUE)){
                logger.info("请求参数值 : " + Arrays.toString(joinPoint.getArgs()));
            }else {
                logger.info("请求参数值 : " + Arrays.toString(joinPoint.getArgs()));
            }
        }
    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("响应RESPONSE : " + ret);
        logger.info("响应时间SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed(joinPoint.getArgs());
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    @Deprecated
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
