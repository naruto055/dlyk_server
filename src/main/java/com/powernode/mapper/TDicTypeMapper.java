package com.powernode.mapper;

import com.powernode.model.TDicType;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_dic_type(字典类型表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TDicType
*/
public interface TDicTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TDicType record);

    int insertSelective(TDicType record);

    TDicType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDicType record);

    int updateByPrimaryKey(TDicType record);

    List<TDicType> selectByAll();
}
