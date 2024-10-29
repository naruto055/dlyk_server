package com.powernode.config.handler;

import com.powernode.constant.Constants;
import com.powernode.model.TUser;
import com.powernode.result.R;
import com.powernode.service.RedisService;
import com.powernode.util.JSONUtils;
import com.powernode.util.JWTUtils;
import com.powernode.util.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 登录成功，执行该方法，在该方法中返回json给前端，就可以了
        TUser tUser = (TUser) authentication.getPrincipal();
        // 转为json对象
        String userJSON = JSONUtils.toJSON(tUser);

        // 生成jwt
        String jwt = JWTUtils.createJWT(userJSON);
        // 写入redis
        redisService.setValue(Constants.REDIS_JWT_KEY + tUser.getId(), jwt);
        // 设置jwt的过期时间
        String rememberMe = request.getParameter("rememberMe");
        if (Boolean.parseBoolean(rememberMe)) {
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.EXPIRE_TIME, TimeUnit.SECONDS);
        } else {
            redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        // 登录成功的统一结果
        R result = R.OK(jwt);

        // 把R对象转成json
        String resultJSON = JSONUtils.toJSON(result);

        // 把R对象以json返回给前端
        ResponseUtils.write(response, resultJSON);
    }
}
