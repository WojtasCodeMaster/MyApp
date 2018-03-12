package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {


    @RequestMapping("/hello")
    public String helloClass(@RequestParam(defaultValue = "Elo Mordy") String text, Model model){
        model.addAttribute("message","z request param "+ text);
        return "resultPage";

    }
}
