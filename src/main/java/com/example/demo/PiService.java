package com.example.demo;

import com.example.demo.entities.Data;
import com.example.demo.entities.Energy;
import com.example.demo.entities.Settings;
import com.example.demo.repositories.EnergyEfficientRepository;
import com.example.demo.services.DataService;
import com.example.demo.services.SettingService;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PiService {

    //Raspberry pi ports enable
    //private final GpioController gpio = GpioFactory.getInstance();
    //public final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyRelay", PinState.HIGH);
    //public final GpioPinDigitalOutput pinLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "led", PinState.LOW);

    double temperature;
    double userTemp;
    String operationMode;
    private int workingTime;
    long startTime;
    long stopTime;
    int elapsedTimeInSeconds;

    @Autowired
    SettingService settingService;
    @Autowired
    EnergyEfficientRepository energyEfficientRepository;
    @Autowired
    DataService dataService;



    public double getTemperature() {
        return temperature;
    }

    public void setUserTemp(double userTemp) {
        this.userTemp = userTemp;
    }


    public double getUserTemp() {
        return userTemp;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    @Scheduled(fixedRate = 500)
    public void reportCurrentTime() {
        if (operationMode == null){
            this.setOperationMode(settingService.getOneByName("operation_mode").getSettingsValue());
        }

        if (operationMode.equals("temperature") || operationMode.equals("schedule")) {
            if (temperature < userTemp) {
                controlRelay(1);
            } else {
                controlRelay(0);
            }
        }
    }

    public void manual(String status) {
        if (status.equals("on")) {
            controlRelay(1);
        } else if (status.equals("off")) {
            controlRelay(0);
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void updateStatus() {

        System.out.println(LocalDateTime.now().getDayOfWeek());
        System.out.println(LocalDateTime.now().getHour());

        if (this.operationMode.equals("schedule")) {

            List<Settings> settingsList = settingService.getSettings();

            String weekString = String.valueOf(LocalDateTime.now().getDayOfWeek());
            int currentHour = LocalDateTime.now().getHour();

            switch (weekString) {
                case "MONDAY":
                    if (currentHour == Integer.valueOf(settingsList.get(0).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(1).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(2).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(3).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(4).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(5).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(6).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(7).getSettingsValue()));
                    }
                    break;
                case "TUESDAY":
                    if (currentHour == Integer.valueOf(settingsList.get(8).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(9).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(10).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(11).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(12).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(13).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(14).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(15).getSettingsValue()));
                    }
                    break;
                case "WEDNESDAY":
                    if (currentHour == Integer.valueOf(settingsList.get(16).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(17).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(18).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(19).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(20).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(21).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(22).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(23).getSettingsValue()));
                    }
                    break;
                case "THURSDAY":
                    if (currentHour == Integer.valueOf(settingsList.get(24).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(25).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(26).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(27).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(28).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(29).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(30).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(31).getSettingsValue()));
                    }
                    break;
                case "FRIDAY":
                    if (currentHour == Integer.valueOf(settingsList.get(32).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(33).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(34).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(35).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(36).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(37).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(38).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(39).getSettingsValue()));
                    }
                    break;
                case "SATURDAY":
                    if (currentHour == Integer.valueOf(settingsList.get(40).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(41).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(42).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(43).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(44).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(45).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(46).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(47).getSettingsValue()));
                    }
                    break;
                case "SUNDAY":
                    if (currentHour == Integer.valueOf(settingsList.get(48).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(49).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(50).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(51).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(52).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(53).getSettingsValue()));
                    } else if (currentHour == Integer.valueOf(settingsList.get(54).getSettingsValue())) {
                        this.setUserTemp(Integer.valueOf(settingsList.get(55).getSettingsValue()));
                    }
                    break;
            }
        }
    }

    private void controlRelay(int code)
    {

        if (code == 0){
            //pin.high();
            stopTime = System.nanoTime();
            long elapsedTime = stopTime - startTime;
            elapsedTimeInSeconds = (int)(elapsedTime /  1000000000.0);
            energyEfficientRepository.save(new Energy(elapsedTimeInSeconds, new Timestamp(System.currentTimeMillis())));
        }else if (code == 1){
            startTime = System.nanoTime();
            //pin.low();
        }

    }


    //reads temperature from file every one second
    //@Scheduled(fixedRate = 1000)
    public void readTemp() {
        String path = "/sys/bus/w1/devices/10-0008033786c3/w1_slave";
        String line;
        double currentTemp = 0.0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();
            line = br.readLine();
            String[] argrs = line.split("=");
            currentTemp = Double.parseDouble(argrs[1]) / 1000;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        temperature = Double.valueOf(twoDForm.format(currentTemp));
    }
    @Scheduled(fixedRate = 1000 * 60)
    private void addRecortToChart(){
        Data data = new Data();
        data.setTemperature(temperature);
        this.dataService.save(data);
    }
}
