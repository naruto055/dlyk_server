package com.powernode.result;

import lombok.*;

/**
 * 枚举类
 * <p>
 * 数得过来的东西，可以用枚举来定义
 * <p>
 * 一年有12个月，一周有7天，那我们的状态码信息是数得过来的，几十个，最多一两个百个
 */

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public enum CodeEnum {

    OK(200, "成功"),

    FAIL(500, "失败"),


    LOGIN_JWT_IS_EMPTY(901, "请求Token为空"),

    LOGIN_JWT_IS_ILLEGAL(902, "请求Token不合法"),

    LOGIN_JWT_IS_EXPIRE(903, "请求Token已过期"),

    LOGIN_JWT_NO_MATCH(904, "请求Token不正确"),

    USER_LOGOUT(200, "退出成功"),

    DATA_ACCESS_EXCEPTION(500, "数据操作失败"),

    ACCESS_DENIED(500, "权限不足");

    private int code;

    @NonNull
    private String msg;

}
