package com.powernode.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

/**
 * 用户表
 *
 * @TableName t_user
 */
@Data
public class TUser implements Serializable, UserDetails {
    /**
     * 主键，自动增长，用户ID
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账户是否没有过期，0已过期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密码是否没有过期，0已过期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 账号是否没有锁定，0已锁定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 账号是否启用，0禁用 1启用
     */
    private Integer accountEnabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    /**
     * 角色List
     */
    private List<String> roleList;

    /**
     * 权限标识符List
     */
    private List<String> permissionList;

    /**
     * 用户菜单list
     */
    private List<TPermission> menuPermissionList;

    /**
     * 一对一关联查询
     */
    private TUser createByDO;
    private TUser editByDO;


    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TUser other = (TUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getLoginAct() == null ? other.getLoginAct() == null : this.getLoginAct().equals(other.getLoginAct()))
                && (this.getLoginPwd() == null ? other.getLoginPwd() == null : this.getLoginPwd().equals(other.getLoginPwd()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getAccountNoExpired() == null ? other.getAccountNoExpired() == null : this.getAccountNoExpired().equals(other.getAccountNoExpired()))
                && (this.getCredentialsNoExpired() == null ? other.getCredentialsNoExpired() == null : this.getCredentialsNoExpired().equals(other.getCredentialsNoExpired()))
                && (this.getAccountNoLocked() == null ? other.getAccountNoLocked() == null : this.getAccountNoLocked().equals(other.getAccountNoLocked()))
                && (this.getAccountEnabled() == null ? other.getAccountEnabled() == null : this.getAccountEnabled().equals(other.getAccountEnabled()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
                && (this.getEditTime() == null ? other.getEditTime() == null : this.getEditTime().equals(other.getEditTime()))
                && (this.getEditBy() == null ? other.getEditBy() == null : this.getEditBy().equals(other.getEditBy()))
                && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLoginAct() == null) ? 0 : getLoginAct().hashCode());
        result = prime * result + ((getLoginPwd() == null) ? 0 : getLoginPwd().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAccountNoExpired() == null) ? 0 : getAccountNoExpired().hashCode());
        result = prime * result + ((getCredentialsNoExpired() == null) ? 0 : getCredentialsNoExpired().hashCode());
        result = prime * result + ((getAccountNoLocked() == null) ? 0 : getAccountNoLocked().hashCode());
        result = prime * result + ((getAccountEnabled() == null) ? 0 : getAccountEnabled().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getEditTime() == null) ? 0 : getEditTime().hashCode());
        result = prime * result + ((getEditBy() == null) ? 0 : getEditBy().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginAct=").append(loginAct);
        sb.append(", loginPwd=").append(loginPwd);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", accountNoExpired=").append(accountNoExpired);
        sb.append(", credentialsNoExpired=").append(credentialsNoExpired);
        sb.append(", accountNoLocked=").append(accountNoLocked);
        sb.append(", accountEnabled=").append(accountEnabled);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", editTime=").append(editTime);
        sb.append(", editBy=").append(editBy);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        // 角色
        if (!ObjectUtils.isEmpty(this.getRoleList())) {
            this.getRoleList().forEach(role -> {
                list.add(new SimpleGrantedAuthority(role));
            });
        }

        // 权限标识符
        if (!ObjectUtils.isEmpty(this.getPermissionList())) {
            this.getPermissionList().forEach(permission -> {
                list.add(new SimpleGrantedAuthority(permission));
            });
        }
        return list;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.getLoginPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getLoginAct();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.getAccountNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.getAccountNoLocked() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.getCredentialsNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.getAccountEnabled() == 1;
    }
}