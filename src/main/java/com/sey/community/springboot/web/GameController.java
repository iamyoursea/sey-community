package com.sey.community.springboot.web;

import com.sey.community.springboot.config.auth.LoginUser;
import com.sey.community.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller@RequiredArgsConstructor
public class GameController {

    @GetMapping("/games")
    public ModelAndView gameHome(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user);
            model.addAttribute("requestFrom", "games");
        }
        return new ModelAndView("sey-games");
    }
}
