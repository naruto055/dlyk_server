package com.powernode.mapper;

import com.powernode.commons.DataScope;
import com.powernode.model.TClueRemark;
import com.powernode.query.ClueRemarkQuery;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_clue_remark(线索跟踪记录表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TClueRemark
*/
public interface TClueRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);

    @DataScope(tableAlias = "tcr", tableField = "create_by")
    List<TClueRemark> selectClueRemarkByPage(ClueRemarkQuery clueRemarkQuery);
}
