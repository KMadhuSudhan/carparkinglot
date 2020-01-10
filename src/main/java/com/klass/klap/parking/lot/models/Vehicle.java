package com.klass.klap.parking.lot.models;

import com.klass.klap.parking.lot.enums.Color;

public abstract class Vehicle {
    protected String vehicleNo;
    protected Color color;
    protected Slot slot;

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
