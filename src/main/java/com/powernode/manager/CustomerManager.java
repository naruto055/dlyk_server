package com.powernode.manager;

import com.auth0.jwt.JWT;
import com.powernode.mapper.TClueMapper;
import com.powernode.mapper.TCustomerMapper;
import com.powernode.model.TClue;
import com.powernode.model.TCustomer;
import com.powernode.model.TUser;
import com.powernode.query.CustomerQuery;
import com.powernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class CustomerManager {
    @Resource
    private TCustomerMapper tCustomerMapper;

    @Resource
    private TClueMapper tClueMapper;

    @Transactional(rollbackFor = Exception.class)
    public Boolean convertCustomer(CustomerQuery customerQuery) {
        // 1. 验证该线索是否已经转过客户，转过了就不能再转
        TClue tClue = tClueMapper.selectByPrimaryKey(customerQuery.getClueId());
        if (tClue.getState() == -1) {
            throw new RuntimeException("该线索已经被转为客户");
        }

        // 2. 向客户表插入一条数据
        TCustomer tCustomer = new TCustomer();
        BeanUtils.copyProperties(customerQuery, tCustomer);
        Integer loginId = JWTUtils.parseUserFromJWT(customerQuery.getToken()).getId();
        tCustomer.setCreateBy(loginId);
        tCustomer.setCreateTime(new Date());

        int insert = tCustomerMapper.insertSelective(tCustomer);

        // 3. 把线索表的数据状态改为-1
        TClue clue = new TClue();
        clue.setId(customerQuery.getClueId());
        clue.setState(-1);
        int update = tClueMapper.updateByPrimaryKeySelective(clue);

        return insert >= 1 && update >= 1;
    }
}
