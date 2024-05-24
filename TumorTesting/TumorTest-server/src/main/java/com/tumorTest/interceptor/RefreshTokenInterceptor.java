package com.tumorTest.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.tumorTest.constant.JwtClaimsConstant;
import com.tumorTest.constant.RedisConstant;
import com.tumorTest.context.BaseContext;
import com.tumorTest.dto.UserDto;
import com.tumorTest.excption.LoginException;
import com.tumorTest.properties.JwtProperties;
import com.tumorTest.uitl.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Component
@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 校验token，对所有请求进行拦截，查看请求是否有token。有的话刷新。
     * 不管有没有token都放心。对所有请求都有一个刷新token的作用。
     *  而对于token的拦截在TokenInterceptor进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //1、从请求头中获取令牌
        String token = request.getHeader("token");
        //2、校验令牌
        try {
            // user：token：+  获取缓存，如果没有抛个异常
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(RedisConstant.USER_TOKEN+token);
            if (entries.isEmpty() || entries == null) {
                return true;
            }
            // 把缓存放进threadLocal
            UserDto userDto = BeanUtil.fillBeanWithMap(entries, new UserDto(), false);
            BaseContext.setUser(userDto);
            // 将token续期
            redisTemplate.expire(RedisConstant.USER_TOKEN+token,RedisConstant.TOKEN_TIME, TimeUnit.MINUTES);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}
