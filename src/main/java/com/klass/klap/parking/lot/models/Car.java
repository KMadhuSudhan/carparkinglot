package com.klass.klap.parking.lot.models;

import com.klass.klap.parking.lot.enums.Color;

public class Car extends Vehicle {
    public Car(String vehicleNo, Color color) {
        this.vehicleNo = vehicleNo;
        this.color = color;
    }
}
