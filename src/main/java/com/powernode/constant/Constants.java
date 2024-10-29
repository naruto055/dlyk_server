package com.powernode.constant;

/**
 * 常量类
 */
public class Constants {
    public static final String LOGIN_URI = "/api/login";

    public static final String REDIS_JWT_KEY = "dlyk:user:login";

    //  jwt过期时间 7天
    public static final long EXPIRE_TIME = 7 * 24 * 60 * 60L;

    // jwt过期时间 30分钟
    public static final long DEFAULT_EXPIRE_TIME = 30 * 60L;

    // 每页10条数据
    public static final int PAGE_SIZE = 10;

    // 请求头token 的名称
    public static final String TOKEN_NAME = "Authorization";

    // redis负责人的key
    public static final String REDIS_OWNER_KEY = "dlyk:user:owner";

    public static final String EXCEL_FILE_NAME = "客户信息数据";
}
