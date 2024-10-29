package com.powernode.service;

import com.github.pagehelper.PageInfo;
import com.powernode.model.TClueRemark;
import com.powernode.query.ClueRemarkQuery;

public interface ClueRemarkService {

    int saveClueRemark(ClueRemarkQuery clueRemarkQuery);

    PageInfo<TClueRemark> getClueRemark(Integer current, ClueRemarkQuery clueRemarkQuery);
}
