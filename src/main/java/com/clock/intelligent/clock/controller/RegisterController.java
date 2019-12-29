package com.clock.intelligent.clock.controller;

import com.clock.intelligent.clock.mapper.ClockUserMapper;
import com.clock.intelligent.clock.model.ClockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Controller
public class RegisterController {
    @Autowired
    ClockUserController clockUserController;

    @RequestMapping(value = "/Register",method = {RequestMethod.POST, RequestMethod.GET})
    public String register(@RequestParam(value = "accountid", required = false) String accountid,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "serialNumber", required = false) String serialNumber,
                           HttpServletRequest request,
                           Model model) {
        model.addAttribute("accountid", accountid);
        model.addAttribute("password", password);
        model.addAttribute("name", name);
        model.addAttribute("serialNumber", serialNumber);
        if (accountid == null || accountid == "") {
            model.addAttribute("error", "账号不能为空");
            return "Register";
        }
        if (password == null || password == "") {
            model.addAttribute("error", "密码不能为空");
            return "Register";
        }
        ClockUser user=new ClockUser();
//        String token= UUID.randomUUID().toString();
         user.setAccountid(accountid);
         user.setPassword(password);
         user.setName(name);
         user.setSerialNumber(serialNumber);
         if (user!=null){
             clockUserController.findAndUpdateClockUser(user);
         }

        return "redirect:/";
    }
}
