package com.powernode.web;

import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.model.TActivityRemark;
import com.powernode.query.ActivityRemarkQuery;
import com.powernode.result.R;
import com.powernode.service.ActivityRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;

    /**
     * 添加活动备注信息
     * @param activityRemarkQuery
     * @param token
     * @return
     */
    @PostMapping("/api/activity/remark")
    public R addActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader(Constants.TOKEN_NAME) String token) {
        activityRemarkQuery.setToken(token);
        int save = activityRemarkService.saveActivityRemark(activityRemarkQuery);
        System.out.println(save);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 分页查询活动备注信息
     * @param current
     * @param id
     * @return
     */
    @GetMapping("/api/activity/remark")
    public R activityRemarkPage(@RequestParam(value = "current", required = false) Integer current,
                                @RequestParam(value = "activityId") Integer id) {
        ActivityRemarkQuery activityRemarkQuery = new ActivityRemarkQuery();
        activityRemarkQuery.setActivityId(id);

        if (current == null) {
            current = 1;
        }
        PageInfo<TActivityRemark> info = activityRemarkService.getActivityRemarkByPage(current, activityRemarkQuery);
        return R.OK(info);
    }

    /**
     * 获取要编辑市场活动备注
     * @param id
     * @return
     */
    @GetMapping("/api/activity/remark/{id}")
    public R editActivityRemark(@PathVariable("id") Integer id) {
        TActivityRemark tActivityRemark = activityRemarkService.getActivityRemarkById(id);
        return R.OK(tActivityRemark);
    }

    /**
     * 修改活动备注信息
     * @param query
     * @param token
     * @return
     */
    @PutMapping("/api/activity/remark")
    public R editActivityRemark(@RequestBody ActivityRemarkQuery query, @RequestHeader(Constants.TOKEN_NAME) String token) {
        query.setToken(token);
        int update = activityRemarkService.updateActivityRemark(query);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/activity/remark/{id}")
    public R delActivityRemark(@PathVariable("id") Integer id){
        int del = activityRemarkService.delActivityRemarkById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }
}
