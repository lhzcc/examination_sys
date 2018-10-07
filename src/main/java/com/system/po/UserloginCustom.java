package com.system.po;

/**
 * Userlogin扩展类
 */
public class UserloginCustom extends Userlogin {

    private Role role_ob;

    public void setRole(Role role) {
        this.role_ob = role;
    }

    public Role getRole_ob() {
        return role_ob;
    }

}
