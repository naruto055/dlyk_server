package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.mapper.TActivityRemarkMapper;
import com.powernode.model.TActivityRemark;
import com.powernode.query.ActivityRemarkQuery;
import com.powernode.query.BaseQuery;
import com.powernode.service.ActivityRemarkService;
import com.powernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private TActivityRemarkMapper tActivityRemarkMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();
        BeanUtils.copyProperties(activityRemarkQuery, tActivityRemark);
        tActivityRemark.setCreateTime(new Date());
        Integer id = JWTUtils.parseUserFromJWT(activityRemarkQuery.getToken()).getId();
        tActivityRemark.setCreateBy(id);
        return tActivityRemarkMapper.insertSelective(tActivityRemark);
    }

    @Override
    public PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TActivityRemark> list = tActivityRemarkMapper.selectActivityRemarkByPage(activityRemarkQuery);
        PageInfo<TActivityRemark> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TActivityRemark getActivityRemarkById(Integer id) {
        TActivityRemark tActivityRemark = tActivityRemarkMapper.selectByPrimaryKey(id);
        return tActivityRemark;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateActivityRemark(ActivityRemarkQuery query) {
        TActivityRemark tActivityRemark = new TActivityRemark();
        BeanUtils.copyProperties(query, tActivityRemark);
        tActivityRemark.setEditTime(new Date());
        Integer loginId = JWTUtils.parseUserFromJWT(query.getToken()).getId();
        tActivityRemark.setEditBy(loginId);
        int update = tActivityRemarkMapper.updateByPrimaryKeySelective(tActivityRemark);
        return update;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delActivityRemarkById(Integer id) {
        // 逻辑删除：不删除数据，只修改数据的状态，数据仍然还在数据库中
        TActivityRemark tActivityRemark = new TActivityRemark();
        tActivityRemark.setId(id);
        tActivityRemark.setDeleted(1);  // 1表示删除
        int update = tActivityRemarkMapper.updateByPrimaryKeySelective(tActivityRemark);
        return update;
    }
}
