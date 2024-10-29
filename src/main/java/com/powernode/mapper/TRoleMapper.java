package com.powernode.mapper;

import com.powernode.model.TRole;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_role(角色表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TRole
*/
public interface TRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TRole record);

    int insertSelective(TRole record);

    TRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

    List<TRole> selectByUserId(Integer userId);
}
