package com.powernode.service;

import com.github.pagehelper.PageInfo;
import com.powernode.model.TActivity;
import com.powernode.query.ActivityQuery;

import java.util.List;

public interface ActivityPageService {
    PageInfo<TActivity> getActivitiesByPage(Integer current, ActivityQuery activityQuery);

    int savaActivity(ActivityQuery activityQuery);

    TActivity getActivityById(Integer id);

    int updateActivity(ActivityQuery activityQuery);

    TActivity selectActivityDetail(Integer id);

    int deleteActivityById(Integer id);

    int batchDeleteActivities(List<String> idList);

    List<TActivity> getOnGoingActivity();
}
