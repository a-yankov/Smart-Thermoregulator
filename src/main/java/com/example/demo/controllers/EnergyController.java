package com.example.demo.controllers;

import com.example.demo.entities.Energy;
import com.example.demo.entities.Settings;
import com.example.demo.services.EnergyEfficientService;
import com.example.demo.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import java.util.Set;

@Controller
public class EnergyController {

    @Autowired
    EnergyEfficientService energyEfficientService;
    @Autowired
    SettingService settingService;

    @GetMapping("/energy-efficient")
    public String getEnergyEfficientPage(Model model, @RequestParam(name = "fromDate", required = false) Date fromDate,
                                         @RequestParam(name = "tiDate", required = false)  Date toDate){
// @DateTimeFormat(pattern="yyyy-MM-dd")
        int workTime = 0;
       // if (fromDate != null && toDate != null) {
           workTime = this.energyEfficientService.getWorkingTime(fromDate, toDate);
      //  }else{
      //      workTime = this.energyEfficientService.getWorkingTime();
      //  }

        System.out.println("workingTime Variable: " + workTime);

        Settings powerSettings = settingService.getOneByName("wats");
        Settings priceSettings = settingService.getOneByName("electricity_day_price");
        System.out.println(powerSettings.getSettingsValue());

        double kiloWatts = Integer.valueOf(powerSettings.getSettingsValue()) / 1000.0;
        double workTimeInHours = workTime / 60.0 / 60.0;
        double electricityPrice = Double.valueOf(priceSettings.getSettingsValue());


        Double total =  (kiloWatts * workTimeInHours) * electricityPrice;

        System.out.println(total);
        model.addAttribute("consumption", total);
        model.addAttribute("ad", priceSettings.getSettingsValue());
        model.addAttribute("power", powerSettings.getSettingsValue());
        model.addAttribute("price", priceSettings.getSettingsValue());

        int power = Integer.parseInt(powerSettings.getSettingsValue());
        return "energy-efficient";
    }

    @PostMapping("/energy-efficient")
    public String postEnergyEfficientPage(@RequestParam("power") String power, @RequestParam("price") String price){

        Settings PowerSettings = this.settingService.getOneByName("wats");
        Settings priceSettings = this.settingService.getOneByName("electricity_day_price");


        PowerSettings.setSettingsValue(power);
      priceSettings.setSettingsValue(price);
        this.settingService.saveSetting(PowerSettings);
        this.settingService.saveSetting(priceSettings);

        return "redirect:/energy-efficient";
    }
}

