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

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    PiService piService;

    @Autowired
    DataService dataService;

    @Autowired
    SettingService settingService;



    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("operation_mode", piService.getOperationMode());
        model.addAttribute("temp", piService.getTemperature());

        model.addAttribute("userTemp", piService.getUserTemp());
        model.addAttribute("operation_mode", this.settingService.getOneByName("operation_mode").getSettingsValue());

        String status;
        if (this.piService.pin.isHigh() == true){
            status = "Off";
        }else {
            status = "On";
        }
        model.addAttribute("status", status);

        String[] array = {"schedule", "temperature", "manual"};
        model.addAttribute("op_mode_sequence", array);


        return "home";
    }
    @GetMapping("/by-temp")
    public String byTemperatureGet(Model model){
        model.addAttribute("user_selected_temp", this.settingService.getOneByName("user_selected_temp").getSettingsValue());
        return "temperature";
    }

    @PostMapping("/by-temp")
    public String byTemperaturePost(@RequestParam("user_selected_temperature") String userSelectedTemperature){


        String operationModeString = "temperature";

        Settings settingMode = this.settingService.getOneByName("operation_mode");
        settingMode.setSettingsValue(operationModeString);
        this.settingService.saveSetting(settingMode);


        Settings settingUserTemp = this.settingService.getOneByName("user_selected_temp");
        settingUserTemp.setSettingsValue(userSelectedTemperature);
        this.settingService.saveSetting(settingUserTemp);

        this.piService.setOperationMode("temperature");
        this.piService.setUserTemp(Double.parseDouble(userSelectedTemperature));

        return "redirect:/";
    }

    @GetMapping("/previous-data")
    public String data(Model model){
        Set<Data> dataSet = this.dataService.findAll();

        model.addAttribute("data", dataSet);
        return "data";
    }

    @GetMapping("/by-schedule")
    public String byScheduleGet(Model model){

        List<Settings> settings = this.settingService.getSettings();
        Map<String, String> map = new HashMap<>();
        for (Settings setting : settings) {
            map.put(setting.getSettingsKey(), setting.getSettingsValue());
        }

        model.addAttribute("settings", map);

        return "schedule";
    }

    @PostMapping("/by-schedule")
    public String bySchedulePost(@RequestParam Map<String,String> requestParams){
        List<Settings> settings = this.settingService.getSettings();
        for (Settings setting : settings) {
              String value = requestParams.get(setting.getSettingsKey());
              setting.setSettingsValue(value);
        }
        this.settingService.saveSettings(settings);
        this.piService.setOperationMode("schedule");
        return "redirect:/";
    }

    @GetMapping("/by-manual")
    public String byManualGet(){
        return "manual";
    }

    @PostMapping("/by-manual")
    public String byManualPost(@RequestParam("operation_mode") String operationMode, @RequestParam("manual_select") String manualSelect){

        Settings settingMode = this.settingService.getOneByName("operation_mode");
        settingMode.setSettingsValue(operationMode);
        this.settingService.saveSetting(settingMode);

        this.piService.manual(manualSelect);
        this.piService.setOperationMode("manual");

        return "redirect:/";
    }
}
