package com.powernode.web;

import com.github.pagehelper.PageInfo;
import com.powernode.commons.DataScope;
import com.powernode.model.TUser;
import com.powernode.query.UserQuery;
import com.powernode.result.R;
import com.powernode.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取登录人信息
     * @param authentication
     * @return
     */
    @GetMapping(value = "/api/login/info")
    public R loginInfo(Authentication authentication) {
        TUser tUser = (TUser) authentication.getPrincipal();
        return R.OK(tUser);
    }

    /**
     * 用户勾选记住我，免登陆
     * @return
     */
    @GetMapping("/api/login/free")
    public R freeLogin() {
        return R.OK();
    }

    /**
     * 分页查询
     * @param current
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:lsit')")
    @GetMapping("/api/users")
    public R userPage(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) {
            current = 1;
        }
        PageInfo<TUser> pageInfo = userService.getUserByPage(current);
        return R.OK(pageInfo);
    }

    /**
     * 获取用户详情数据
     * @param id
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:view')")
    @GetMapping("/api/user/{id}")
    public R userDetail(@PathVariable(value = "id") Integer id) {
        TUser tUser = userService.getUserById(id);
        return R.OK(tUser);
    }

    /**
     * 新增用户
     * @param userQuery
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:add')")
    @PostMapping("/api/user")
    public R addUser(UserQuery userQuery, @RequestHeader("Authorization") String token) {
        userQuery.setToken(token);
        int save = userService.saveUser(userQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 编辑用户
     * @param userQuery
     * @param token
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:edit')")
    @PutMapping(value = "/api/user")
    public R editUser(UserQuery userQuery, @RequestHeader("Authorization") String token) {
        userQuery.setToken(token);
        int update = userService.update(userQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:delete')")
    @DeleteMapping(value = "/api/user/{id}")
    public R deleteUser(@PathVariable(value = "id") Integer id) {
        int del = userService.delUserById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PreAuthorize(value = "hasAuthority('user:delete')")
    @DeleteMapping(value = "/api/user")
    public R batchDelUser(@RequestParam(value = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = userService.batchDelUserIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }
}
