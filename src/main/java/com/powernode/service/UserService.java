package com.powernode.service;

import com.github.pagehelper.PageInfo;
import com.powernode.model.TUser;
import com.powernode.query.UserQuery;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserById(Integer id);

    int saveUser(UserQuery userQuery);

    int update(UserQuery userQuery);

    int delUserById(Integer id);

    int batchDelUserIds(List<String> idList);

    List<TUser> getOwnerList();
}
