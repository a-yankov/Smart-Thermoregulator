//package com.example.demo.pi;
//
//import com.example.demo.PiService;
//import com.pi4j.io.gpio.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class Rp {
//    final GpioController gpio = GpioFactory.getInstance();
//    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyRelay", PinState.HIGH);
//    public String status = "";
//    public int degree;
//
//    @Autowired
//    PiService piService;
//
//    public void doSomething(){
//        pin.low();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        pin.high();
//
//    }
//
//    public void useRelay(String status){
//        if (status.equals("On")){
//            pin.low();
//        }else if (status.equals("Off")){
//            pin.high();
//        }
//    }
//
//    @Scheduled(fixedRate = 500)
//    public void reportCurrentTime() {
//        double temp = piService.readTemp();
//        if (status.equals("Auto")){
//            if (temp < degree){
//                pin.low();
//            }else {
//                pin.high();
//            }
//        }
//    }
//}
