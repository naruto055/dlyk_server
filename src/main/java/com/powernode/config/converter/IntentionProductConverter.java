package com.powernode.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.powernode.DlykServerApplication;
import com.powernode.model.TDicValue;
import com.powernode.model.TProduct;
import com.powernode.result.DicEnum;

import java.util.List;


/**
 * 意向产品转换器
 * excel中的   比亚迪   转为   java中的   2
 */
public class IntentionProductConverter implements Converter<Integer> {
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData,
                                     ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) throws Exception {

        String cellIntentionStateName = cellData.getStringValue();
        List<TProduct> tDicValueList = (List<TProduct>) DlykServerApplication.cacheMap.get(DicEnum.PRODUCT.getCode());

        for (TProduct tProduct : tDicValueList) {
            Integer id = tProduct.getId();
            String name = tProduct.getName();

            if (cellIntentionStateName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
