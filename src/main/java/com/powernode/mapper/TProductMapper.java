package com.powernode.mapper;

import com.powernode.model.TProduct;

import java.util.List;

/**
* @author wenqunsheng
* @description 针对表【t_product(产品表)】的数据库操作Mapper
* @createDate 2024-03-14 17:51:12
* @Entity com.powernode.model.TProduct
*/
public interface TProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);

    List<TProduct> selectAllOnSaleProduct();
}
