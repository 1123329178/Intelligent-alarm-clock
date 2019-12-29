package com.clock.intelligent.clock.controller;

import com.clock.intelligent.clock.mapper.ClockUserMapper;
import com.clock.intelligent.clock.mapper.ClookMapper;
import com.clock.intelligent.clock.model.ClockUser;
import com.clock.intelligent.clock.model.Clook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    ClockUserMapper clockUserMapper;
    @Autowired
    ClookMapper clookMapper;

    @GetMapping("/") //这个注解的
    public String index(HttpServletRequest request,
                        Model model)
//                        @RequestParam(name = "page", defaultValue = "1") Integer page,
//                        @RequestParam(name = "size", defaultValue = "5") Integer size,
//                        @RequestParam(name = "search", required = false) String search,
//                        @RequestParam(name = "tag", required = false) String tag)
                        {
        //当访问首页的时候  查找Cookie    到数据库里去查
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token"))//成功
                {
                    String token = cookie.getValue();
                    ClockUser user = clockUserMapper.findClockUserBySerialNumber(token);

                    if (user!= null) {
                        request.getSession().setAttribute("user", user);
                        //只有登录了才显示,自己设置的音乐
                        List<Clook> allClook = clookMapper.findAllClook(user.getId());
                        model.addAttribute("allClook", allClook);
                    }
                    break;
                }
            }
        }

        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
