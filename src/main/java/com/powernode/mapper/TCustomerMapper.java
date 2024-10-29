package com.powernode.mapper;

import com.powernode.model.TCustomer;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_customer(客户表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TCustomer
*/
public interface TCustomerMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TCustomer record);

    int insertSelective(TCustomer record);

    TCustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCustomer record);

    int updateByPrimaryKey(TCustomer record);

    List<TCustomer> selectCustomerByPage(Integer current);

    List<TCustomer> selectCustomerByExcel(List<String> idList);

    Integer selectByCount();
}
