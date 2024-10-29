package com.powernode.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.powernode.model.TUser;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt的工具类
 *
 */
public class JWTUtils {
    public static final String SECRET = "dY8300olWQ3345;1d<3w48";

    /**
     * 生成jwt
     *
     * @return
     */
    public static String createJWT(String userJSON) {
        //组装头数据
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        //链式编程
        return JWT.create()
                //头部
                .withHeader(header)

                //负载(可以有多个)
                .withClaim("user", userJSON)

                //签名
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证jwt
     *
     */
    public static Boolean verifyJWT(String jwt) {
        try {
            // 使用密钥创建一个jwt验证器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            //jwt验证器验证jwt
            jwtVerifier.verify(jwt); //如果此行代码没有抛出异常，就说明jwt验证通过，抛出异常，就说明jwt验证不通过

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     */
    public static String parseJWT(String jwt) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 使用密钥创建一个jwt验证器对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            //jwt验证器对象验证jwt，得到一个解码后的jwt对象
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

            //验证通过了，就开始解析负载里面的数据
            Claim userJSON = decodedJWT.getClaim("user");

            return userJSON.asString();
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static TUser parseUserFromJWT(String jwt) {
        try {
            // 使用秘钥创建一个验证器对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            //验证JWT，得到一个解码后的jwt对象
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

            //通过解码后的jwt对象，就可以获取里面的负载数据
            Claim userClaim = decodedJWT.getClaim("user");

            String userJSON = userClaim.asString();

            return JSONUtils.toBean(userJSON, TUser.class);
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
