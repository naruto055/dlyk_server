package com.powernode.config.filter;

import com.powernode.constant.Constants;
import com.powernode.model.TUser;
import com.powernode.result.CodeEnum;
import com.powernode.result.R;
import com.powernode.service.RedisService;
import com.powernode.util.JSONUtils;
import com.powernode.util.JWTUtils;
import com.powernode.util.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class TokenVerifyFilter extends OncePerRequestFilter {
    @Resource
    private RedisService redisService;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(Constants.LOGIN_URI)) { // 如果是登录请求，此时还没有生成jwt，那不需要对登录请求进行jwt验证
            // 验证jwt通过了 ，让Filter链继续执行，也就是继续执行下一个Filter
            filterChain.doFilter(request, response);
        } else {

            String token = null;
            if (request.getRequestURI().equals("/api/exportExcel")) {
                token = request.getParameter(Constants.TOKEN_NAME);
            } else {
                token = request.getHeader(Constants.TOKEN_NAME);
            }
            if (!StringUtils.hasText(token)) {
                // token验证未通过的统一结果
                R result = R.FAIL(CodeEnum.LOGIN_JWT_IS_EMPTY);
                // 把R对象转成json
                String resultJSON = JSONUtils.toJSON(result);
                // 把R以json返回给前端
                ResponseUtils.write(response, resultJSON);
                return;
            }

            // 验证token有没有被篡改过
            if (!JWTUtils.verifyJWT(token)) {
                // token验证未通过统一结果
                R result = R.FAIL(CodeEnum.LOGIN_JWT_IS_ILLEGAL);

                // 把R对象转成json
                String resultJSON = JSONUtils.toJSON(result);

                // 把R以json返回给前端
                ResponseUtils.write(response, resultJSON);

                return;
            }

            // 拿到redis中的token，跟请求头中的token进行比对
            TUser tUser = JWTUtils.parseUserFromJWT(token);
            String redisToken = (String) redisService.getValue(Constants.REDIS_JWT_KEY + tUser.getId());

            if (!StringUtils.hasText(redisToken)) {
                // token验证未通过统一结果
                R result = R.FAIL(CodeEnum.LOGIN_JWT_IS_EXPIRE);

                // 把R对象转成json
                String resultJSON = JSONUtils.toJSON(result);

                // 把R以json返回给前端
                ResponseUtils.write(response, resultJSON);

                return;
            }

            if (!token.equals(redisToken)) {
                // token验证未通过的统一结果
                R result = R.FAIL(CodeEnum.LOGIN_JWT_NO_MATCH);

                // 把R对象转成json
                String resultJSON = JSONUtils.toJSON(result);

                // 把R以json返回给前端
                ResponseUtils.write(response, resultJSON);
                return;
            }

            // jwt验证通过了，那么在spring security的上下文环境中要设置一下，设置当前这个人是登录过的，你后续不要再拦截他了
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tUser, tUser.getLoginPwd(), tUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 刷新一下token(异步处理)
            /*new Thread(()-> {
                // 刷新token
                String rememberMe = request.getHeader("rememberMe");
                if (Boolean.parseBoolean(rememberMe)) {
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.EXPIRE_TIME, TimeUnit.SECONDS);
                } else {
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                }
            }).start();*/

            // 异步处理，使用线程池
            threadPoolTaskExecutor.execute(() -> {
                // 刷新token
                String rememberMe = request.getHeader("rememberMe");
                if (Boolean.parseBoolean(rememberMe)) {
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.EXPIRE_TIME, TimeUnit.SECONDS);
                } else {
                    redisService.expire(Constants.REDIS_JWT_KEY + tUser.getId(), Constants.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                }
            });

            // 验证jwt通过
            filterChain.doFilter(request, response);
        }

    }
}
