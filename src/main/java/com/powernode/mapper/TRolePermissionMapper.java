package com.powernode.mapper;

import com.powernode.model.TRolePermission;

/**
* @author wenqunsheng
* @description 针对表【t_role_permission(角色权限关系表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TRolePermission
*/
public interface TRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TRolePermission record);

    int insertSelective(TRolePermission record);

    TRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TRolePermission record);

    int updateByPrimaryKey(TRolePermission record);

}
