package com.luhan.controller;

import com.luhan.annotation.RateLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈测试@RateLimit注解,是否会产生作用〉<br>
 *
 * @author luHan
 * @create 2019-08-31 09:53
 * @since 1.0.0
 */
@RestController
@RequestMapping("/testRateLimit")
public class TestRateLimitController {
    @RequestMapping(value = "/test",produces = "text/html;charset=UTF-8")
    @RateLimit(value = 2)
    public String testRateLimit(){
        return "访问成功";
    }
}
