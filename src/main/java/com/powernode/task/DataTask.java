package com.powernode.task;

import com.powernode.DlykServerApplication;
import com.powernode.model.TActivity;
import com.powernode.model.TDicType;
import com.powernode.model.TProduct;
import com.powernode.result.DicEnum;
import com.powernode.service.ActivityPageService;
import com.powernode.service.DicTypeService;
import com.powernode.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
public class DataTask {

    @Resource
    private DicTypeService dicTypeService;

    @Resource
    private ProductService productService;

    @Resource(name = "activityPageServiceImpl")
    private ActivityPageService activityService;

    // 调度的意思
    @Scheduled(fixedDelayString = "${project.task.delay}",
            zone = "Asia/Shanghai", initialDelay = 1000)
    public void task() {
        // 定时任务要执行的业务逻辑代码
        // System.out.println("定时任务业务逻辑执行......" + new Date());

        List<TDicType> dicTypeList = dicTypeService.loadAllDicData();

        dicTypeList.forEach(tDicType -> {
            String typeCode = tDicType.getTypeCode();
            List<TDicType> dicValueList = tDicType.getDicValueList();
            DlykServerApplication.cacheMap.put(typeCode, dicValueList);
        });

        // 查询在售的所有产品
        List<TProduct> tProductList = productService.getOnSaleProduct();
        DlykServerApplication.cacheMap.put(DicEnum.PRODUCT.getCode(), tProductList);

        // 查询所有正在进行的市场活动
        List<TActivity> tActivities = activityService.getOnGoingActivity();
        DlykServerApplication.cacheMap.put(DicEnum.ACTIVITY.getCode(), tActivities);
    }
}
