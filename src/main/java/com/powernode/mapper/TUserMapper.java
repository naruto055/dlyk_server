package com.powernode.mapper;

import com.powernode.commons.DataScope;
import com.powernode.model.TUser;
import com.powernode.query.BaseQuery;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_user(用户表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:13
* @Entity com.powernode.model.TUser
*/
public interface TUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByLoginAct(String username);

    @DataScope(tableAlias = "tu", tableField = "id")
    List<TUser> selectUserByPage(BaseQuery query);

    TUser selectDetailById(Integer id);

    int deleteByIds(List<String> idList);

    List<TUser> selectByOwner();
}
