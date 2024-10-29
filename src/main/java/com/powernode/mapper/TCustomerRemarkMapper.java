package com.powernode.mapper;

import com.powernode.model.TCustomerRemark;

/**
* @author wenqunsheng
* @description 针对表【t_customer_remark(客户跟踪记录表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TCustomerRemark
*/
public interface TCustomerRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TCustomerRemark record);

    int insertSelective(TCustomerRemark record);

    TCustomerRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TCustomerRemark record);

    int updateByPrimaryKey(TCustomerRemark record);

}
