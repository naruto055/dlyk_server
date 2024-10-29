package com.powernode.service.impl;

import com.powernode.mapper.TProductMapper;
import com.powernode.model.TProduct;
import com.powernode.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private TProductMapper tProductMapper;

    @Override
    public List<TProduct> getOnSaleProduct() {
        return tProductMapper.selectAllOnSaleProduct();
    }
}
