package com.powernode.aspect;

import com.powernode.commons.DataScope;
import com.powernode.constant.Constants;
import com.powernode.model.TUser;
import com.powernode.query.BaseQuery;
import com.powernode.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
@Aspect
public class DataScopeAspect {
    @Pointcut(value = "@annotation(com.powernode.commons.DataScope)")
    public void pointCut() {

    }

    @Around(value = "pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();

        // 拿到方法的注解
        DataScope dataScope = methodSignature.getMethod().getDeclaredAnnotation(DataScope.class);

        // 拿到注解里的数据
        String tableAlias = dataScope.tableAlias();
        String tabledField = dataScope.tableField();

        // 获取请求头中的token，解析，知道是谁
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constants.TOKEN_NAME);
        TUser tUser = JWTUtils.parseUserFromJWT(token);

        // 拿到用户的角色
        List<String> roleList = tUser.getRoleList();

        // 组装SQL语句的过滤条件
        if (!roleList.contains("admin")) {
            // 查员工自己的数据
            Object params = joinPoint.getArgs()[0];
            if (params instanceof BaseQuery) {
                BaseQuery query = (BaseQuery) params;

                query.setFilterSQL(" and " + tableAlias + "." + tabledField + "=" + tUser.getId());
            }
        }
        // System.out.println("目标方法执行之前");
        Object result = joinPoint.proceed();
        // System.out.println("目标方法执行之后");
        return result;
    }
}
