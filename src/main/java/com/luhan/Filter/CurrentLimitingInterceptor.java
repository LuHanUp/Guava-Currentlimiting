package com.luhan.Filter;

import com.google.common.util.concurrent.RateLimiter;
import com.luhan.enums.ReponseEnum;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈通过Guava的RateLimiter实现限流〉<br>
 *      RateLimiter采用的是令牌桶算法
 * @author luHan
 * @create 2019-08-29 18:25
 * @since 1.0.0
 */
@Component("currentLimitingInterceptor")
public class CurrentLimitingInterceptor extends AbstractInterceptor{
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2);
    @Override
    protected ReponseEnum currentLimiting(HttpServletRequest request) {
        if(RATE_LIMITER.tryAcquire()){
            return ReponseEnum.OK;
        }
        return ReponseEnum.FAIL;
    }
}
