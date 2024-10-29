package com.powernode.mapper;

import com.powernode.commons.DataScope;
import com.powernode.model.TActivityRemark;
import com.powernode.query.ActivityRemarkQuery;
import com.powernode.query.BaseQuery;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_activity_remark(市场活动备注表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TActivityRemark
*/
public interface TActivityRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    @DataScope(tableAlias = "tar", tableField = "create_by")
    List<TActivityRemark> selectActivityRemarkByPage(ActivityRemarkQuery activityRemarkQuery);
}
