package com.powernode.service;

import com.powernode.result.NameValue;
import com.powernode.result.SummaryData;

import java.util.List;

public interface StatisticService {
    SummaryData loadSummaryData();

    List<NameValue> loadSaleFunnelData();

    List<NameValue> loadSourcePieData();
}
