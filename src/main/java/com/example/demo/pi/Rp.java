package com.example.demo.pi;

import com.pi4j.io.gpio.*;
        import org.springframework.stereotype.Service;

@Service
public class Rp {
    final GpioController gpio = GpioFactory.getInstance();
    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);

    public void doSomething(){
        pin.low();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pin.high();

    }
}
