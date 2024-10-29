package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.manager.CustomerManager;
import com.powernode.mapper.TCustomerMapper;
import com.powernode.model.TCustomer;
import com.powernode.query.CustomerQuery;
import com.powernode.result.CustomerExcel;
import com.powernode.service.CustomerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerManager customerManager;

    @Resource
    private TCustomerMapper tCustomerMapper;

    @Override
    public Boolean convertCustomer(CustomerQuery customerQuery) {
        return customerManager.convertCustomer(customerQuery);
    }

    @Override
    public PageInfo<TCustomer> getCustomers(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TCustomer> list = tCustomerMapper.selectCustomerByPage(current);
        PageInfo<TCustomer> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<CustomerExcel> getCustomersByExcel(List<String> idList) {
        List<CustomerExcel> customerExcelList = new ArrayList<>();

        List<TCustomer> list = tCustomerMapper.selectCustomerByExcel(idList);
        // 把从数据库中查询出来的数据，转化为List<CustomerExcel>数据
        list.forEach(data -> {
            CustomerExcel customerExcel = new CustomerExcel();

            customerExcel.setOwnerName(ObjectUtils.isEmpty(data.getOwnerDO()) ? "" : data.getOwnerDO().getName());
            customerExcel.setActivityName(ObjectUtils.isEmpty(data.getOwnerDO()) ? "" : data.getActivityDO().getName());
            customerExcel.setFullName(data.getClueDO().getFullName());
            customerExcel.setAppellationName(ObjectUtils.isEmpty(data.getAppellationDO()) ? "" : data.getAppellationDO().getTypeValue());
            customerExcel.setPhone(data.getClueDO().getPhone());
            customerExcel.setWeixin(data.getClueDO().getWeixin());
            customerExcel.setQq(data.getClueDO().getQq());
            customerExcel.setEmail(data.getClueDO().getEmail());
            customerExcel.setAge(data.getClueDO().getAge());
            customerExcel.setJob(data.getClueDO().getJob());
            customerExcel.setYearIncome(data.getClueDO().getYearIncome());
            customerExcel.setAddress(data.getClueDO().getAddress());
            customerExcel.setNeedLoadName(ObjectUtils.isEmpty(data.getNeedLoanDO()) ? "" : data.getNeedLoanDO().getTypeValue());
            customerExcel.setProductName(ObjectUtils.isEmpty(data.getIntentionProductDO()) ? "" : data.getIntentionProductDO().getName());
            customerExcel.setSourceName(ObjectUtils.isEmpty(data.getSourceDO()) ? "" : data.getSourceDO().getTypeValue());
            customerExcel.setDescription(data.getDescription());
            customerExcel.setNextContactTime(data.getNextContactTime());

            customerExcelList.add(customerExcel);

        });
        return customerExcelList;
    }
}
