package com.example.demo.controllers;

import com.example.demo.PiService;
import com.example.demo.entities.SchedulerSettings;
import com.example.demo.entities.Settings;
import com.example.demo.services.SchedulerSettingsService;
import com.example.demo.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Controller
public class SchedulerController {

    @Autowired
    SchedulerSettingsService schedulerSettingsService;
    @Autowired
    PiService piService;
    @Autowired
    SettingService settingService;

    @GetMapping("/setOperationModeScheduler")
    public String setOperationModeScheduler(){
        this.piService.setOperationMode("schedule");
        Settings settings = this.settingService.getOneByName("operation_mode");
        settings.setSettingsValue("schedule");
        this.settingService.saveSetting(settings);
        return "redirect:/by-schedule";
    }

    @GetMapping("/by-schedule")
    public String getChe(Model model){
        List<SchedulerSettings> list = this.schedulerSettingsService.findAll();
        model.addAttribute("list", list);
        return "sched";
    }

    @PostMapping("/by-schedule")
    public String PostCher(@RequestParam("day") int day, @RequestParam("mode") String mode , @RequestParam("time") @DateTimeFormat(pattern = "HH:mm") Date time, @RequestParam("temp") int temp){
        System.out.println(day);
        System.out.println(time);

        System.out.println(mode);
        SchedulerSettings schedulerSettings = new SchedulerSettings();
        schedulerSettings.setDay(day);
        schedulerSettings.setMode(mode);
        schedulerSettings.setTime(new Time(time.getTime()));
        if (mode.equals("Temp")){
            schedulerSettings.setTemp(temp);
        }

        schedulerSettingsService.addSchedulerSettingsLine(schedulerSettings);
        return "redirect:/by-schedule";
    }

    @GetMapping("/del/{id}")
    public String delRecord(@PathVariable("id") long id){
        this.schedulerSettingsService.delete(id);
        return  "redirect:/by-schedule";
    }
}
