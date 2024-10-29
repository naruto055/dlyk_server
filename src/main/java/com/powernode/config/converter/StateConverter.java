package com.powernode.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.powernode.DlykServerApplication;
import com.powernode.model.TDicValue;
import com.powernode.result.DicEnum;

import java.util.List;

/**
 * 线索状态转换器
 */
public class StateConverter implements Converter<Integer> {
    /**
     * 把excel中的数据转换为java中的数据
     * excel中的“已联系”  转为  Java类中的  27
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData,
                                     ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) throws Exception {

        String cellStateName = cellData.getStringValue();
        List<TDicValue> tDicValueList = (List<TDicValue>) DlykServerApplication.cacheMap.get(DicEnum.STATE.getCode());

        for (TDicValue tDicValue : tDicValueList) {
            Integer id = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if (cellStateName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
