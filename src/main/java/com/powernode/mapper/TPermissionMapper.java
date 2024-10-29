package com.powernode.mapper;

import com.powernode.model.TPermission;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_permission(权限表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TPermission
*/
public interface TPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    TPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TPermission record);

    int updateByPrimaryKey(TPermission record);

    List<TPermission> selectMenuPermissionByUserId(Integer userId);

    List<TPermission> selectButtonPermissionByUserId(Integer userId);
}
