package com.example.demo.controllers;

import com.example.demo.PiService;
import com.example.demo.pi.Rp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    PiService piService;
    @Autowired
    Rp rp;
    String status;

    @GetMapping("/")
    public String getHome(Model model){
        double temp = piService.readTemp();
        model.addAttribute("status", status);
        model.addAttribute("temp", temp);
        rp.doSomething();
        return "home";
    }

    @PostMapping("/")
    public String postHome(@RequestParam("order") String order){
        rp.doSomething();
        status = order;

        if (order.equals("On")){

        }else if(order.equals("Off")){

        }else if(order.equals("Auto")){

        }

        return "redirect:/";
    }

    @GetMapping("/previous-data")
    public String data(){
        return "data";
    }
}
