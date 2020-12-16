package com.team3.weekday.controller;

import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.ChannelSessionManagement;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.UUID;

/**
 * @author yangke
 * @title: LoginController
 * @projectName weekday
 * @date 2020-09-14
 */

@Controller
public class LoginController {

    private Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private IDataBus dataBus;

    // use for direct to login page
    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /* no use
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
        return "test/test-login";
    }
    */

    // use for register
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email,
                               @RequestParam("phone") Long phonenum,
                               @RequestParam("birth") String birthday,
                               @RequestParam("label") String label) throws Exception {

        Date birth = CommonConstant.DATE_FORMAT.parse(birthday);

        // hash password
        password = CommonConstant.passwordEncoder.encode(password);
        User user = dataBus.userService().creatUser(username, password, email, phonenum, birth, label);
        dataBus.emailSenderService().sendActiveEmail(user);
        return "redirect:/index";
    }

    //forget password
    @GetMapping("/forgetPassword")
    public String forgetPassword() { return "main/forgetPassword";}

    @PostMapping("/forgetPassword")
    public String forgetPassword(@RequestParam("email") String email) throws Exception {
        User user =dataBus.userService().getUserByEmail(email);
        if (user != null) {
            dataBus.emailSenderService().sendResetPwdEmail(email);
            return String.format("redirect:/index?success=%s","true");
        }
        return String.format("redirect:/forgetPassword?success=%s","false");//alert: user
    }

    @GetMapping("/resetPassword")
    public String resetPassword() { return "email/resetPassword";}

    @PostMapping(value = "/resetPassword")// /{url}
    public String resetPassword(@RequestParam("password")String newPwd,
                                @RequestParam("encode") String encode) throws Exception {
        String param = URLDecoder.decode(encode, "UTF-8");
        String[] paramList = (String[]) ConvertUtils.convert(param.split(";"), String.class);
        String email = paramList[0];
        Long expTime = Long.valueOf(paramList[1]);
        Long curTime = System.currentTimeMillis();
        if (expTime<=curTime){
            return String.format("redirect:/resetPassword?success=%s","expired");
        } else {
            User user = dataBus.userService().getUserByEmail(email);
            String password = CommonConstant.passwordEncoder.encode(newPwd);
            user.setPassword(password);
            dataBus.userRepository().save(user);
        }
        return String.format("redirect:/index?success=%s","resetSuccess");
    }

    // check code
    @RequestMapping(value = "/checkCode")
    public String checkCode(String code) {
        User user = dataBus.userRepository().findByActiveCode(code);
        if (user != null) {
            user.setActiveStatus(1);//state change to active
            user.setActiveCode(""); //delete code
            dataBus.userService().updateUser(user);
            return "email/activeSuccess";
        }
        return "redirect:/index";
    }

    @GetMapping(value = "/github")
    public String oauthByGitHub(@RequestParam(value = "code") String code) throws Exception {
        String resp = dataBus.githubAuthorityService().getAccessToken(code);

        JSONObject userInfo = dataBus.githubAuthorityService().getUserInfo(resp);

        //signup
        Long userId = ((Integer) userInfo.get("id")).longValue();
        User user = dataBus.userRepository().findByGithubId(userId);
        if (user == null) {
            user = dataBus.userService().creatUserByGithub(userId, (String) userInfo.get("login"), (String) userInfo.get("avatar_url"));
        }

        code = UUID.randomUUID().toString();
        ChannelSessionManagement.getInstance().addChannelSession(new ChannelSession(user.getId(), code));
        return String.format("redirect:/index?userId=%s&code=%s", user.getId(), code);
    }

    @GetMapping(value = "/google")
    public String oauthByGoogle(@RequestParam(value = "tokenId") String tokenId) throws Exception {
        log.info("Google login successful!");
        JSONObject userInfo = dataBus.googleAuthorityService().getUserInfo(tokenId);
        log.info("Google userInfo!"+ userInfo.toString());
        String googleId = (String) userInfo.get("id");
        User user = dataBus.userRepository().findByGoogleId(googleId);
        if (user == null) {
            user = dataBus.userService().creatUserByGoogle(googleId, (String)userInfo.get("name"), (String)userInfo.get("avatar"));
        }
        String code = UUID.randomUUID().toString();
        ChannelSessionManagement.getInstance().addChannelSession(new ChannelSession(user.getId(), code));
        return String.format("redirect:/index?userId=%s&code=%s", user.getId(), code);
    }
}
