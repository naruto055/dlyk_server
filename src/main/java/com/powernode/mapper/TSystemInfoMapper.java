package com.powernode.mapper;

import com.powernode.model.TSystemInfo;

/**
* @author wenqunsheng
* @description 针对表【t_system_info(系统信息表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TSystemInfo
*/
public interface TSystemInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TSystemInfo record);

    int insertSelective(TSystemInfo record);

    TSystemInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSystemInfo record);

    int updateByPrimaryKey(TSystemInfo record);

}
