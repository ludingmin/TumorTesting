package com.tumorTest.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.tumorTest.context.BaseContext;
import com.tumorTest.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class TokenInterceptor implements HandlerInterceptor {

    /*
        这个拦截器是对于token的拦截
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        UserDto user = BaseContext.getUser();
        if (user == null) {
            //如果本地线程没有User信息则拦截
            response.setStatus(401);
            return false;
        }

        return true;
    }


}
