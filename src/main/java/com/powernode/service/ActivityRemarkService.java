package com.powernode.service;

import com.github.pagehelper.PageInfo;
import com.powernode.model.TActivityRemark;
import com.powernode.query.ActivityRemarkQuery;

public interface ActivityRemarkService {

    int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery);

    TActivityRemark getActivityRemarkById(Integer id);

    int updateActivityRemark(ActivityRemarkQuery query);

    int delActivityRemarkById(Integer id);
}
