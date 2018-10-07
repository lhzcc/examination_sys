package com.system.controller;

import com.system.po.Userlogin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    //鐧诲綍璺宠浆
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String loginUI() throws Exception {
        return "../../login";
    }

    //鐧诲綍琛ㄥ崟澶勭悊
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(Userlogin userlogin) throws Exception {

        //Shiro瀹炵幇鐧诲綍
        UsernamePasswordToken token = new UsernamePasswordToken(userlogin.getUsername(),
                userlogin.getPassword());
        Subject subject = SecurityUtils.getSubject();

        //濡傛灉鑾峰彇涓嶅埌鐢ㄦ埛鍚嶅氨鏄櫥褰曞け璐ワ紝浣嗙櫥褰曞け璐ョ殑璇濓紝浼氱洿鎺ユ姏鍑哄紓甯�
        subject.login(token);

        if (subject.hasRole("admin")) {
            return "redirect:/admin/showStudent";
        } else if (subject.hasRole("teacher")) {
            return "redirect:/teacher/showCourse";
        } else if (subject.hasRole("student")) {
            return "redirect:/student/showCourse";
        }

        return "/login";
    }

}
