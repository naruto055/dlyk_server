package com.powernode.web;

import com.github.pagehelper.PageInfo;
import com.powernode.model.TActivity;
import com.powernode.model.TUser;
import com.powernode.query.ActivityQuery;
import com.powernode.result.R;
import com.powernode.service.ActivityPageService;
import com.powernode.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.powernode.constant.Constants.TOKEN_NAME;

@RestController
public class ActivityController {
    @Resource(name = "activityPageServiceImpl")
    private ActivityPageService activityService;

    @Resource
    private UserService userService;

    /**
     * 分页查询活动列表
     * @param current
     * @return
     */
    @GetMapping(value = "/api/activities")
    public R activityPage(@RequestParam(value = "current", required = false) Integer current, ActivityQuery activityQuery) {
        if (current == null) current = 1;
        PageInfo<TActivity> pageInfo = activityService.getActivitiesByPage(current, activityQuery);
        return R.OK(pageInfo);
    }

    /**
     * 查询负责人
     * @return
     */
    @GetMapping(value = "/api/owner")
    public R owner() {
        List<TUser> userList = userService.getOwnerList();
        return R.OK(userList);
    }

    /**
     * 新增市场活动
     * @param activityQuery
     * @param token
     * @return
     */
    @PostMapping(value = "/api/activity")
    public R addActivity(ActivityQuery activityQuery, @RequestHeader(value = TOKEN_NAME) String token) {
        activityQuery.setToken(token);
        int save = activityService.savaActivity(activityQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 获取要编辑的市场活动数据
     * @param id
     * @return
     */
    @GetMapping(value = "/api/activity/{id}")
    public R getActivityById(@PathVariable(value = "id") Integer id) {
        TActivity tActivity = activityService.getActivityById(id);
        return R.OK(tActivity);
    }

    /**
     * 编辑市场活动
     * @param activityQuery
     * @param token
     * @return
     */
    @PutMapping(value = "/api/activity")
    public R edit(ActivityQuery activityQuery, @RequestHeader(value = TOKEN_NAME) String token) {
        activityQuery.setToken(token);
        int update = activityService.updateActivity(activityQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id删除市场活动
     * @param id
     * @return
     */
    @DeleteMapping("/api/activity/{id}")
    public R delActivity(@PathVariable("id") Integer id) {
        int del = activityService.deleteActivityById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 批量删除活动
     * @param ids
     * @return
     */
    @DeleteMapping("/api/activities")
    public R batchDeleteActivity(@RequestParam("ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int del = activityService.batchDeleteActivities(idList);
        return del >= idList.size() ? R.OK() : R.FAIL();
    }

}
