package com.powernode.web;

import com.powernode.DlykServerApplication;
import com.powernode.model.TActivity;
import com.powernode.model.TDicValue;
import com.powernode.model.TProduct;
import com.powernode.result.DicEnum;
import com.powernode.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DicController {
    @GetMapping("/api/dicValue/{typeCode}")
    public R dicData(@PathVariable("typeCode") String typeCode) {

        if (typeCode.equals(DicEnum.ACTIVITY.getCode())) {
            List<TActivity> tActivityList = (List<TActivity>) DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tActivityList);
        } else if (typeCode.equals(DicEnum.PRODUCT.getCode())) {
            List<TProduct> tProductList = (List<TProduct>) DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tProductList);
        } else {
            List<TDicValue> tDicValueList = (List<TDicValue>) DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tDicValueList);
        }

    }
}
