package com.h9.admin.security;

import com.h9.admin.model.po.Permission;
import com.h9.admin.model.po.Role;
import com.h9.admin.model.po.SystemUser;
import com.h9.admin.service.SystemUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author: George
 * @date: 2017/10/30 19:02
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemUserService systemUserService;

    // 权限验证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+" + principalCollection.toString());
        SystemUser user = systemUserService.getByName((String) principalCollection.getPrimaryPrincipal());
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),
                SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Map<String, Set> codeMechantIds = new HashMap<String, Set>();
        Set userPermissions = new HashSet();
        for (Role r : user.getRoles()) {
            // 赋予角色
            info.addRole(r.getName());
            // 赋予权限
            for (Permission per : r.getPermissions()) {
                userPermissions.add(per.getAccessCode());
                if (per.getType() == null || "".equals(per.getType())) {
                    continue;
                }
                info.addStringPermission(per.getAccessCode());
            }
        }
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userPermissions", userPermissions);
        return info;
    }

    // 用户验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +" + authenticationToken.toString());
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        logger.info(userName + String.copyValueOf(token.getPassword()));
        String password = String.copyValueOf(token.getPassword());
        SystemUser user = systemUserService.getByNameAndPassword(userName, password);
        if (user != null) {
            logger.info("user" + userName + "login authenticate success");
            // 设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            return new SimpleAuthenticationInfo(userName, password, getName());
        } else {
            logger.error("user " + userName + " authenticate faild");
            return null;
        }
    }

}
