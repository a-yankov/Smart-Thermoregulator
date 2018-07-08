package com.example.demo.controllers;

import com.example.demo.entities.Energy;
import com.example.demo.entities.Settings;
import com.example.demo.services.EnergyEfficientService;
import com.example.demo.services.SettingService;
import groovy.util.IFileNameFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.Date;

import java.util.Optional;
import java.util.Set;

@Controller
public class EnergyController {

    @Autowired
    EnergyEfficientService energyEfficientService;
    @Autowired
    SettingService settingService;

    @GetMapping("/energy-efficient")
    public String getEnergyEfficientPage(Model model, @RequestParam(name = "fromDate", required = false )  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> fromDate,
                                         @RequestParam(name = "toDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Optional<Date> toDate){
// @DateTimeFormat(pattern="yyyy-MM-dd")
        int workTime = 0;



        if (fromDate != null && toDate != null) {
            if (fromDate.isPresent() && toDate.isPresent()){
                workTime = this.energyEfficientService.getWorkingTime(fromDate.get(), toDate.get());
            }else {
                workTime = this.energyEfficientService.getWorkingTime();
            }
        }else{
           workTime = this.energyEfficientService.getWorkingTime();
        }


        Settings powerSettings = settingService.getOneByName("wats");
        Settings priceSettings = settingService.getOneByName("electricity_day_price");


        double kiloWatts = Integer.valueOf(powerSettings.getSettingsValue()) / 1000.0;
        double workTimeInHours = workTime / 60.0 / 60.0;
        double electricityPrice = Double.valueOf(priceSettings.getSettingsValue());


        Double total =  (kiloWatts * workTimeInHours) * electricityPrice;
        DecimalFormat df = new DecimalFormat("#.##");


        model.addAttribute("consumption", df.format(total));
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

