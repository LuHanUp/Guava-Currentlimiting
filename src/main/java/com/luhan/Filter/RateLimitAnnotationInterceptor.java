package com.luhan.Filter;

import com.google.common.util.concurrent.RateLimiter;
import com.luhan.enums.RateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈针对@RateLimit注解的限流处理器〉<br>
 * 采用的也是Guava的RateLimiter
 *
 * @author luHan
 * @create 2019-08-29 19:29
 * @since 1.0.0
 */
@Component
@Scope
@Aspect
public class RateLimitAnnotationInterceptor {
    private static final Map<String, RateLimiter> cacheRateLimit = new ConcurrentHashMap<>();
    @Autowired
    private HttpServletResponse response;

    @Pointcut("@annotation(com.luhan.enums.RateLimit)")
    public void serviceLimit() {
    }

    @Before("serviceLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        //获取拦截的方法名
        Signature sig = joinPoint.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        if (!currentMethod.isAnnotationPresent(RateLimit.class)) {
            //执行方法
            obj = joinPoint.proceed();
            return obj;
        }
        //获取注解信息
        RateLimit annotation = currentMethod.getAnnotation(RateLimit.class);
        int limitNum = annotation.value(); //获取注解每秒加入桶中的token
        String functionName = msig.getName(); // 注解所在方法名区分不同的限流策略
        RateLimiter rateLimiter = null;
        //获取rateLimiter
        if (cacheRateLimit.containsKey(functionName)) {
            rateLimiter = cacheRateLimit.get(functionName);
        } else {
            cacheRateLimit.put(functionName, RateLimiter.create(limitNum));
            rateLimiter = cacheRateLimit.get(functionName);
        }

        if (rateLimiter.tryAcquire()) {
            //执行方法
            obj = joinPoint.proceed();
        } else {
            //拒绝了请求（服务限流）
            String result = "您被限流了";
            outErrorResult(result);
        }
        return obj;
    }

    //将结果返回
    public void outErrorResult(String result) {
        response.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(result.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
