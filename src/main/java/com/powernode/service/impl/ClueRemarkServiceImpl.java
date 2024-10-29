package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.mapper.TClueRemarkMapper;
import com.powernode.model.TClueRemark;
import com.powernode.query.ClueRemarkQuery;
import com.powernode.service.ClueRemarkService;
import com.powernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Resource
    private TClueRemarkMapper tClueRemarkMapper;

    @Override
    public int saveClueRemark(ClueRemarkQuery clueRemarkQuery) {
        TClueRemark tClueRemark = new TClueRemark();
        BeanUtils.copyProperties(clueRemarkQuery, tClueRemark);
        Integer loginId = JWTUtils.parseUserFromJWT(clueRemarkQuery.getToken()).getId();
        tClueRemark.setCreateBy(loginId);
        tClueRemark.setCreateTime(new Date());
        return tClueRemarkMapper.insertSelective(tClueRemark);
    }

    @Override
    public PageInfo<TClueRemark> getClueRemark(Integer current, ClueRemarkQuery clueRemarkQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TClueRemark> tClueRemarkList = tClueRemarkMapper.selectClueRemarkByPage(clueRemarkQuery);
        PageInfo<TClueRemark> pageInfo = new PageInfo<>(tClueRemarkList);
        return pageInfo;
    }
}
