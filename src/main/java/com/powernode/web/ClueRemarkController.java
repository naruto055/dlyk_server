package com.powernode.web;

import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.model.TClueRemark;
import com.powernode.query.ClueQuery;
import com.powernode.query.ClueRemarkQuery;
import com.powernode.result.R;
import com.powernode.service.ClueRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClueRemarkController {
    @Resource
    private ClueRemarkService clueRemarkService;

    /**
     * 添加线索跟踪记录
     * @param clueRemarkQuery
     * @param token
     * @return
     */
    @PostMapping("/api/clue/remark")
    public R addClueRemark(@RequestBody ClueRemarkQuery clueRemarkQuery, @RequestHeader(Constants.TOKEN_NAME) String token) {
        clueRemarkQuery.setToken(token);
        int count = clueRemarkService.saveClueRemark(clueRemarkQuery);
        return count >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 获取线索跟踪记录
     * @param current
     * @param clueID
     * @return
     */
    @GetMapping("/api/clue/remark")
    public R getClueRemark(@RequestParam("current") Integer current, @RequestParam("clueId") Integer clueID) {
        ClueRemarkQuery clueRemarkQuery = new ClueRemarkQuery();
        clueRemarkQuery.setClueId(clueID);
        if (current == null) current = 1;
        PageInfo<TClueRemark> pageInfo = clueRemarkService.getClueRemark(current, clueRemarkQuery);
        return R.OK(pageInfo);
    }

    // TODO 逻辑删除线索跟踪记录接口
    // TODO 编辑线索跟踪记录接口    这两个接口都要自己去完成的
}
