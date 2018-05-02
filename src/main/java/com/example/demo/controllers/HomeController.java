package com.example.demo.controllers;

import com.example.demo.PiService;

import com.example.demo.entities.Data;
import com.example.demo.entities.Settings;
import com.example.demo.services.DataService;
import com.example.demo.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    PiService piService;

    @Autowired
    DataService dataService;

    @Autowired
    SettingService settingService;

    String status;

    @GetMapping("/")
    public String getHome(Model model){
        List<Settings> settings = this.settingService.getSettings();
        double temp = piService.getTemperature();
        model.addAttribute("status", piService.getStatus());
        model.addAttribute("userTemp", piService.getStatus());
        model.addAttribute("temp", temp);
        model.addAttribute("test", settings.get(0));
        System.out.println(settings.get(0));
        return "home";
    }

    @PostMapping("/")
    public String postHome(@RequestParam("temp") int temp){
        piService.setStatus("Auto");
        piService.setUserTemp(temp);
        return "redirect:/";
    }

    @GetMapping("/on")
    public String postOn(){
        piService.setStatus("On");
        piService.useRelay("On");
        return "redirect:/";
    }

    @GetMapping("/off")
    public String postOff(){
        piService.setStatus("Off");
        piService.useRelay("Off");
        return "redirect:/";
    }

    @GetMapping("/previous-data")
    public String data(Model model){
        Set<Data> dataSet = this.dataService.findAll();

        model.addAttribute("data", dataSet);
        return "data";
    }

    @PostMapping("/by-schedule")
    public String bySchedulePost(@RequestParam Map<String,String> requestParams){
        List<Settings> settings = this.settingService.getSettings();
        for (Settings setting : settings) {
              String value = requestParams.get(setting.getSettingsKey());
              setting.setSettingsValue(value);
        }
        this.settingService.saveSettings(settings);
        return "redirect:/";
    }



    @PostMapping("/by-manual")
    public String byManualPost(@RequestParam() Model model){
        return "redirect:/";
    }
    @PostMapping("/by-temp")
    public String byTempPost(Model model){
        return "redirect:/";
    }
}
