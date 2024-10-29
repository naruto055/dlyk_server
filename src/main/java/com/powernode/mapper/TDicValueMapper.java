package com.powernode.mapper;

import com.powernode.model.TDicValue;

/**
* @author wenqunsheng
* @description 针对表【t_dic_value(字典值表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TDicValue
*/
public interface TDicValueMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TDicValue record);

    int insertSelective(TDicValue record);

    TDicValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDicValue record);

    int updateByPrimaryKey(TDicValue record);

}
