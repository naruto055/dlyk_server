package com.powernode.mapper;

import com.powernode.model.TTranHistory;

/**
* @author wenqunsheng
* @description 针对表【t_tran_history(交易历史记录表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TTranHistory
*/
public interface TTranHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTranHistory record);

    int insertSelective(TTranHistory record);

    TTranHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTranHistory record);

    int updateByPrimaryKey(TTranHistory record);

}
