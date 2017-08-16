package cn.tsjcate.web.site.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 醒悟wjn on 2017/7/17.
 */
@Controller
public class LoginController {
    @RequestMapping(value="login",method= RequestMethod.GET)
    public String login(){
        return "main/login";
    }
}
