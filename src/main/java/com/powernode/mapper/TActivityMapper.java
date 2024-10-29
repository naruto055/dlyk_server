package com.powernode.mapper;

import com.powernode.commons.DataScope;
import com.powernode.model.TActivity;
import com.powernode.query.ActivityQuery;
import com.powernode.query.BaseQuery;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_activity(市场活动表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TActivity
*/
public interface TActivityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    @DataScope(tableAlias = "ta", tableField = "owner_id")
    List<TActivity> selectActivityByPage(ActivityQuery query);


    int deleteByIds(List<String> idList);

    TActivity selectDetailByPrimaryKey(Integer id);

    List<TActivity> selectOnGoingActivity();

    Integer selectByCount();
}
