package com.clock.intelligent.clock.controller;

import com.clock.intelligent.clock.mapper.ClockUserMapper;
import com.clock.intelligent.clock.model.ClockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 页面登录验证层
 *
 * @author hp
 */
@Controller
public class LoginsController {
    @Autowired
    ClockUserMapper clockUserMapper;

    @RequestMapping(value = "/Logins",method = {RequestMethod.POST,RequestMethod.GET})
    public String logins(@RequestParam(value = "accountid", required = false) String accountid,
                         @RequestParam(value = "password", required = false) String password,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         Model model) {
        model.addAttribute("accountid", accountid);
        model.addAttribute("password", password);
        if (accountid == null || accountid == "") {
            model.addAttribute("error", "账号不能为空");
            return "Logins";
        }
        if (password == null || password == "") {
            model.addAttribute("error", "密码不能为空");
            return "Logins";
        }

        ClockUser clockUser = new ClockUser();
        clockUser.setAccountid(accountid);
        clockUser.setPassword(password);
        ClockUser user = clockUserMapper.findClockUserByAccPass(clockUser);

        if (user !=null) {
            model.addAttribute("error", "登陆成功");
        }else
        {
            model.addAttribute("error","登录失败");
            return "Logins";
        }


       // userService.createOrUpdate(user);
        //user.setAvatarUrl(githubUser.getAvatar_url());
          new Cookie("token",user.getSerialNumber());
        // 登录成功，写cookie 和session
        response.addCookie(new Cookie("token",user.getSerialNumber()));
        return "redirect:/"; //redirect 重定向到我所制定的页面去
       // return "/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token","null");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
