package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.mapper.TActivityMapper;
import com.powernode.model.TActivity;
import com.powernode.query.ActivityQuery;
import com.powernode.service.ActivityPageService;
import com.powernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActivityPageServiceImpl implements ActivityPageService {

    @Resource
    private TActivityMapper tActivityMapper;

    @Override
    public PageInfo<TActivity> getActivitiesByPage(Integer current, ActivityQuery activityQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TActivity> list = tActivityMapper.selectActivityByPage(activityQuery);
        PageInfo<TActivity> info = new PageInfo<>(list);
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int savaActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();
        BeanUtils.copyProperties(activityQuery, tActivity);
        tActivity.setCreateTime(new Date());
        Integer loginUserId = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setCreateBy(loginUserId);
        return tActivityMapper.insertSelective(tActivity);
    }

    @Override
    public TActivity getActivityById(Integer id) {
        return tActivityMapper.selectDetailByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();
        BeanUtils.copyProperties(activityQuery, tActivity);
        tActivity.setEditTime(new Date());
        Integer id = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setEditBy(id);
        return tActivityMapper.updateByPrimaryKeySelective(tActivity);
    }

    @Override
    public TActivity selectActivityDetail(Integer id) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteActivityById(Integer id) {
        int del = tActivityMapper.deleteByPrimaryKey(id);
        return del;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDeleteActivities(List<String> idList) {
        return tActivityMapper.deleteByIds(idList);
    }

    @Override
    public List<TActivity> getOnGoingActivity() {
        return tActivityMapper.selectOnGoingActivity();
    }
}
