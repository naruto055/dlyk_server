package com.powernode.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ActivityQuery extends BaseQuery{
    /**
     * 活动的id
     */
    private Integer id;

    /**
     * 负责人id
     */
    private Integer ownerId;

    /**
     * 负责人姓名
     */
    private String name;

    /**
     * 活动创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 活动预算
     */
    private BigDecimal cost;

    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  startTime;

    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  endTime;

    /**
     * 活动描述
     */
    private String description;
}
