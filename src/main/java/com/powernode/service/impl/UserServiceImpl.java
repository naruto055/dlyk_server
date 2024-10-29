package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.constant.Constants;
import com.powernode.manager.RedisManager;
import com.powernode.mapper.TPermissionMapper;
import com.powernode.mapper.TRoleMapper;
import com.powernode.mapper.TUserMapper;
import com.powernode.model.TPermission;
import com.powernode.model.TRole;
import com.powernode.model.TUser;
import com.powernode.query.BaseQuery;
import com.powernode.query.UserQuery;
import com.powernode.service.UserService;
import com.powernode.util.CacheUtils;
import com.powernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private TRoleMapper tRoleMapper;

    @Resource
    private RedisManager redisManager;

    @Resource
    private TPermissionMapper tPermissionMapper;

    /**
     * 登录查询
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);
        if (tUser == null) {
            throw new UsernameNotFoundException("登录账号不存在");
        }

        // 查询当前用户的角色
        List<TRole> tRolesList = tRoleMapper.selectByUserId(tUser.getId());
        ArrayList<String> stringRoleList = new ArrayList<>();
        tRolesList.forEach(tRole -> {
            stringRoleList.add(tRole.getRole());
        });
        tUser.setRoleList(stringRoleList);  //设置用户的角色

        // 查询一下该用户有哪些权限菜单
        List<TPermission> menuPermissionList = tPermissionMapper.selectMenuPermissionByUserId(tUser.getId());
        tUser.setMenuPermissionList(menuPermissionList);

        // 查询该用户有哪些功能权限
        List<TPermission> buttonPermission = tPermissionMapper.selectButtonPermissionByUserId(tUser.getId());
        List<String > stringPermissionList = new ArrayList<>();
        buttonPermission.forEach(tPermission -> {
            stringPermissionList.add(tPermission.getCode());  // 权限标识符
        });
        tUser.setPermissionList(stringPermissionList);  // 设置用户的权限标识符

        return tUser;
    }

    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        // 设置pageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 查询
        List<TUser> list = tUserMapper.selectUserByPage(BaseQuery.builder().build());

        PageInfo<TUser> info = new PageInfo<>(list);

        return info;
    }

    @Override
    public TUser getUserById(Integer id) {
        return tUserMapper.selectDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveUser(UserQuery userQuery) {
        TUser tUser = new TUser();
        // 把userQuery中的数据复制到tUser中去
        BeanUtils.copyProperties(userQuery, tUser);

        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));  // 密码加密
        tUser.setCreateTime(new Date());
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setCreateBy(loginUserId);
        return tUserMapper.insert(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(UserQuery userQuery) {
        TUser tUser = new TUser();
        // 把userQuery中的数据复制到tUser中去
        BeanUtils.copyProperties(userQuery, tUser);

        if (StringUtils.hasText(userQuery.getLoginPwd())) {
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));  // 密码加密
        }

        tUser.setEditTime(new Date());
        // 解析jwt获取登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setEditBy(loginUserId);
        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delUserById(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelUserIds(List<String> idList) {
        return tUserMapper.deleteByIds(idList);
    }

    @Override
    public List<TUser> getOwnerList() {
        // 1. 从redis中查
        // 2. redis查不到，就从数据库中查，并把数据存入redis
        return CacheUtils.getCacheData(()-> {
            // 生产，从redis查数据
            return (List<TUser>) redisManager.getValue(Constants.REDIS_OWNER_KEY);
        }, () -> {
            // 生产，从mysql查询数据
            return (List<TUser>)tUserMapper.selectByOwner();
        }, (t) -> {
            // 消费，把数据放入缓存
            redisManager.setValue(Constants.REDIS_OWNER_KEY, t);
        });
    }
}
