package com.powernode.service.impl;

import com.powernode.mapper.TDicTypeMapper;
import com.powernode.model.TDicType;
import com.powernode.service.DicTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicTypeServiceImpl implements DicTypeService {

    @Resource
    private TDicTypeMapper tDicTypeMapper;

    @Override
    public List<TDicType> loadAllDicData() {

        return tDicTypeMapper.selectByAll();
    }
}
