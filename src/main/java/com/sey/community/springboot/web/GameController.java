package com.sey.community.springboot.web;

import com.sey.community.springboot.config.auth.LoginUser;
import com.sey.community.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller@RequiredArgsConstructor
public class GameController {

    @GetMapping("/games")
    public String gameHome(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user);
        }
        return "games/sey-games";
    }

    @GetMapping("/games/seyCheese")
    public String seyCheese(Model model, @LoginUser SessionUser user) {
        return "/games/seyCheese/index";
    }
}
