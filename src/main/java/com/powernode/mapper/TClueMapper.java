package com.powernode.mapper;

import com.github.pagehelper.PageInfo;
import com.powernode.commons.DataScope;
import com.powernode.model.TClue;
import com.powernode.query.BaseQuery;
import com.powernode.result.NameValue;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_clue(线索表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TClue
*/
public interface TClueMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TClue record);

    int insertSelective(TClue record);

    TClue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClue record);

    int updateByPrimaryKey(TClue record);

    // @DataScope(tableAlias = "", tableField = "")
    List<TClue> selectClueByPage(BaseQuery query);

    void saveClue(List<TClue> cachedDataList);

    int selectByCount(String phone);

    TClue selectDetailById(Integer id);

    Integer selectClueByCount();

    List<NameValue> selectBySource();
}
