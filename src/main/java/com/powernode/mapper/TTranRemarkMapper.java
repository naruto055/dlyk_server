package com.powernode.mapper;

import com.powernode.model.TTranRemark;

/**
* @author wenqunsheng
* @description 针对表【t_tran_remark(交易跟踪记录表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:13
* @Entity com.powernode.model.TTranRemark
*/
public interface TTranRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTranRemark record);

    int insertSelective(TTranRemark record);

    TTranRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTranRemark record);

    int updateByPrimaryKey(TTranRemark record);

}
