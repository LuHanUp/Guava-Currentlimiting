package com.luhan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈HelloWorld Controller〉<br>
 *
 * @author luHan
 * @create 2019-08-29 18:15
 * @since 1.0.0
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    @RequestMapping(value = "/test",produces = "text/html;charset=UTF-8")
    public String testCurrentLimiting(){
        return "访问成功";
    }
}
