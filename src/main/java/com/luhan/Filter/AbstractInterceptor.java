package com.luhan.Filter;

import com.luhan.enums.ReponseEnum;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈抽象的拦截器，具体限流方式让子类实现〉<br>
 *
 * @author luHan
 * @create 2019-08-29 18:16
 * @since 1.0.0
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ReponseEnum reponseEnum = null;
        reponseEnum = currentLimiting(request);
        switch (reponseEnum){
            case OK:// 没有被限流
                return true;
            case FAIL:// 被限流
                // 输出提示语句
                printFail(reponseEnum,response);
                return false;
        }
        printFail(ReponseEnum.FAIL, response);
        return false;
    }

    private void printFail(ReponseEnum reponseEnum, HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(reponseEnum.getDesc());
    }

    /**
     * 抽象的限流方法，让子类完成限流的具体实现
     * @param request
     * @return
     */
    protected abstract ReponseEnum currentLimiting(HttpServletRequest request);
}
