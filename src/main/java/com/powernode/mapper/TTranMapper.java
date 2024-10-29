package com.powernode.mapper;

import com.powernode.model.TTran;

import java.math.BigDecimal;

/**
* @author wenqunsheng
* @description 针对表【t_tran(交易表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TTran
*/
public interface TTranMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTran record);

    int insertSelective(TTran record);

    TTran selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTran record);

    int updateByPrimaryKey(TTran record);

    BigDecimal selectBySuccessTranAmount();

    BigDecimal selectByTotalTranAmount();

    int selectByTotalTranCount();

    int selectBySuccessTranCount();
}
