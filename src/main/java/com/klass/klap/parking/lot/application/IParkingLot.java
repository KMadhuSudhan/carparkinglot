package com.klass.klap.parking.lot.application;

import com.klass.klap.parking.lot.enums.Color;
import com.klass.klap.parking.lot.exceptions.NoCarFound;
import com.klass.klap.parking.lot.exceptions.ParkingNotAvailableException;
import com.klass.klap.parking.lot.models.Car;
import com.klass.klap.parking.lot.models.Slot;

import java.util.List;
import java.util.Map;

public interface IParkingLot {
    public void createParkingLot(int noOfSlots);
    public int park(Car car) throws ParkingNotAvailableException;
    public int leave(int slotNo);
    public Map<Slot, Car> status();
    public List<Car> getRegistrationNumbersForCarsWithColour(Color color);
    public List<Slot> getSlotNumbersForCarsWithColour(Color color);
    public Slot getSlotNumberForRegistrationNumber(String vehicleNo) throws NoCarFound;
}
