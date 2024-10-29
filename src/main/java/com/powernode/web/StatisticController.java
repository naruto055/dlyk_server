package com.powernode.web;

import com.powernode.result.NameValue;
import com.powernode.result.R;
import com.powernode.result.SummaryData;
import com.powernode.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    /**
     * 获取概览数据
     * @return
     */
    @GetMapping(value = "/api/summary/data")
    public R outLineData() {
        SummaryData summaryData = statisticService.loadSummaryData();
        return R.OK(summaryData);
    }

    /**
     * 获取销售漏斗图数据
     * @return
     */
    @GetMapping("/api/saleFunnel/data")
    public R saleFunnelData() {
        List<NameValue> nameValueList = statisticService.loadSaleFunnelData();
        return R.OK(nameValueList);
    }

    @GetMapping("/api/sourcePie/data")
    public R sourcePieChart() {
        List<NameValue> nameValueList = statisticService.loadSourcePieData();
        return R.OK(nameValueList);
    }
}
