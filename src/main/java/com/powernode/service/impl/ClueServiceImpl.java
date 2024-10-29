package com.powernode.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.config.listener.UploadDataListener;
import com.powernode.constant.Constants;
import com.powernode.mapper.TClueMapper;
import com.powernode.model.TClue;
import com.powernode.query.BaseQuery;
import com.powernode.query.ClueQuery;
import com.powernode.service.ClueService;
import com.powernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private TClueMapper tClueMapper;

    @Override
    public PageInfo<TClue> getClueByPage(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TClue> list = tClueMapper.selectClueByPage(BaseQuery.builder().build());
        return new PageInfo<>(list);
    }

    @Override
    public void importExcel(MultipartFile file, String token) throws IOException {
        /**
         * 参数1：要读取的excel文件
         * 参数2：excel模板类
         */
        EasyExcel.read(file.getInputStream(), TClue.class, new UploadDataListener(tClueMapper, token)).sheet().doRead();
    }

    @Override
    public Boolean checkPhone(String phone) {
        int count = tClueMapper.selectByCount(phone);
        return count <= 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClue(ClueQuery clueQuery) {
        int count = tClueMapper.selectByCount(clueQuery.getPhone());

        if (count <= 0) {
            TClue tClue = new TClue();
            BeanUtils.copyProperties(clueQuery, tClue);
            Integer loginId = JWTUtils.parseUserFromJWT(clueQuery.getToken()).getId();
            tClue.setCreateBy(loginId);
            tClue.setCreateTime(new Date());
            return tClueMapper.insertSelective(tClue);
        } else {
            throw new RuntimeException("该手机号已经录入过了，不能再录入");
        }
    }

    @Override
    public TClue getClueById(Integer id) {
        return tClueMapper.selectDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int editClue(ClueQuery clueQuery) {
        TClue tClue = new TClue();
        BeanUtils.copyProperties(clueQuery, tClue);
        Integer loginId = JWTUtils.parseUserFromJWT(clueQuery.getToken()).getId();
        tClue.setEditBy(loginId);
        tClue.setEditTime(new Date());
        return tClueMapper.updateByPrimaryKeySelective(tClue);
    }

    @Override
    public int delClueById(Integer id) {
        return 1;
    }
}
