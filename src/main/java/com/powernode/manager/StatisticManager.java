package com.powernode.manager;

import com.powernode.mapper.TActivityMapper;
import com.powernode.mapper.TClueMapper;
import com.powernode.mapper.TCustomerMapper;
import com.powernode.mapper.TTranMapper;
import com.powernode.result.NameValue;
import com.powernode.result.SummaryData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatisticManager {
    @Resource
    private TActivityMapper tActivityMapper;

    @Resource
    private TClueMapper tClueMapper;

    @Resource
    private TCustomerMapper tCustomerMapper;

    @Resource
    private TTranMapper tTranMapper;

    public SummaryData loadSummaryData() {
        // 有效的市场活动总数
        Integer effectiveActivityCount = tActivityMapper.selectOnGoingActivity().size();

        // 总市场活动数
        Integer totalActivityCount = tActivityMapper.selectByCount();

        // 线索总数
        Integer totalClueCount = tClueMapper.selectClueByCount();

        // 客户总数
        Integer totalCustomerCount = tCustomerMapper.selectByCount();

        // 成功交易额
        BigDecimal successTranAmount = tTranMapper.selectBySuccessTranAmount();

        // 总的交易额
        BigDecimal totalTranAmount = tTranMapper.selectByTotalTranAmount();

        return SummaryData.builder()
                .effectiveActivityCount(effectiveActivityCount)
                .totalActivityCount(totalActivityCount)
                .totalClueCount(totalClueCount)
                .totalCustomerCount(totalCustomerCount)
                .successTranAmount(successTranAmount)
                .totalTranAmount(totalTranAmount)
                .build();
    }

    public List<NameValue> loadSaleFunnelData() {
        List<NameValue> resultList = new ArrayList<>();

        int clueCount = tClueMapper.selectClueByCount();
        int customerCount = tCustomerMapper.selectByCount();
        int tranCount = tTranMapper.selectByTotalTranCount();
        int tranSuccessCount = tTranMapper.selectBySuccessTranCount();
        NameValue clue = NameValue.builder().name("线索").value(clueCount).build();
        resultList.add(clue);

        NameValue customer = NameValue.builder().name("客户").value(customerCount).build();
        resultList.add(customer);

        NameValue tran = NameValue.builder().name("交易").value(tranCount).build();
        resultList.add(tran);

        NameValue tranSuccess = NameValue.builder().name("成交").value(tranSuccessCount).build();
        resultList.add(tranSuccess);

        return resultList;
    }

    public List<NameValue> loadSourcePieData() {
        List<NameValue> nameValueList = tClueMapper.selectBySource();
        return nameValueList;
    }
}
