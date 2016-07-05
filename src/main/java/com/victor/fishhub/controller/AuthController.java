package com.victor.fishhub.controller;

import com.victor.fishhub.service.auth.dto.RegDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String doSignIn() {
        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String doSignUp(Model model) {
        model.addAttribute("regDTO", new RegDTO());
        return "signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register() {

        return "redirect:/";
    }

}
