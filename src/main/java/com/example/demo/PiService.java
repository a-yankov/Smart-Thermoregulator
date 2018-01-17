package com.example.demo;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

@Service
public class PiService {


    public void turnRelayOn(){

    }

    public double readTemp(){
        String path = "/sys/bus/w1/devices/28-0316a36d9bff/w1_slave";
        String line;
        double currentTemp = 0.0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();
            line = br.readLine();
            String[] argrs = line.split("=");
            currentTemp = Math.round(Double.parseDouble(argrs[1]) / 1000);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return  Double.valueOf(twoDForm.format(currentTemp));
    }
}
