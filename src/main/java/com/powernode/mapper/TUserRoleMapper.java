package com.powernode.mapper;

import com.powernode.model.TUserRole;

/**
* @author wenqunsheng
* @description 针对表【t_user_role(用户角色关系表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:13
* @Entity com.powernode.model.TUserRole
*/
public interface TUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TUserRole record);

    int insertSelective(TUserRole record);

    TUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUserRole record);

    int updateByPrimaryKey(TUserRole record);

}
