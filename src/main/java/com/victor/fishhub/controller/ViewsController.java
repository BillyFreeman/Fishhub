/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewsController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goHome(){
        return "home";
    }
    
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn(){
        return "signin";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(){
        return "signup";
    }
    
}
