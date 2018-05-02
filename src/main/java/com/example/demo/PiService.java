package com.example.demo;

import com.pi4j.io.gpio.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

@Service
public class PiService {

    //Raspberry pi ports enable
    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyRelay", PinState.HIGH);

    double temperature;
    double userTemp;
    String status = "";

    public double getTemperature() {
        return temperature;
    }

    public void setUserTemp(double userTemp) {
        this.userTemp = userTemp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }



    @Scheduled(fixedRate = 1000)
    public void readTemp(){
        String path = "/sys/bus/w1/devices/28-0015210e2dee/w1_slave";
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
        temperature =  Double.valueOf(twoDForm.format(currentTemp));
    }

    @Scheduled(fixedRate = 500)
    public void reportCurrentTime() {
        if (status.equals("Auto")){
            if (temperature < userTemp){
                pin.low();
            }else {
                pin.high();
            }
        }
    }

    public void useRelay(String status){
        if (status.equals("On")){
            pin.low();
        }else if (status.equals("Off")){
            pin.high();
        }
    }
}
