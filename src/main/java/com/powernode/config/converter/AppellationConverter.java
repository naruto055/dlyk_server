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
 * 称呼转换器
 * 先生 -> Java类中的 18
 * 女士 -> Java类中的 41
 */
public class AppellationConverter implements Converter<Integer> {
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData,
                                    ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) throws Exception {

        String cellAppellationName = cellData.getStringValue();
        List<TDicValue> tDicValueList = (List<TDicValue>) DlykServerApplication.cacheMap.get(DicEnum.APPELLATION.getCode());

        for (TDicValue tDicValue : tDicValueList) {
            Integer id = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if (cellAppellationName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
