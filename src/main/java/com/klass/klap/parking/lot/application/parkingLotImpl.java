package com.klass.klap.parking.lot.application;

import com.klass.klap.parking.lot.enums.Color;
import com.klass.klap.parking.lot.exceptions.NoCarFound;
import com.klass.klap.parking.lot.exceptions.ParkingNotAvailableException;
import com.klass.klap.parking.lot.models.Car;
import com.klass.klap.parking.lot.models.Slot;

import java.util.*;

public class parkingLotImpl implements IParkingLot {
    private Map<Slot, Car> slotCarMap;
    private Map<Color, List<Car>> colorCarListMap;
    private Map<String, Slot> registrationNumberSlotMap;
    PriorityQueue<Integer> freeSlots = new PriorityQueue<Integer>();

    public parkingLotImpl() {
        slotCarMap = new HashMap<Slot, Car>();
        colorCarListMap = new HashMap<Color, List<Car>>();
        registrationNumberSlotMap = new HashMap<String, Slot>();
    }


    public void createParkingLot(int noOfSlots) {
        for (int slot = 1; slot <= noOfSlots; slot++) {
            slotCarMap.put(new Slot(slot), null);
            freeSlots.add(slot);
        }
        System.out.println("Created a parking lot with " + noOfSlots +  " slots");
    }

    public int park(Car car) throws ParkingNotAvailableException {
        int slotId = getFreeSlot();
        if (slotId > 0) {
            Slot slot = new Slot(slotId);
            System.out.println("Allocated slot number: " + slotId);
            slotCarMap.put(slot, car);
            car.setSlot(slot);
            addToColorCarListMap(car);
            addToRegistrationNumberSlotMap(car, slot);
        } else {
            throw new ParkingNotAvailableException("Parking full");
        }
        return slotId;
    }

    public int leave(int slotNo) {
        Car car = slotCarMap.get(new Slot(slotNo));
        slotCarMap.put(new Slot(slotNo), null);
        removeFromColorCarListMap(car);
        removeFromRegistrationNumberSlotMap(car);
        freeSlots.add(slotNo);
        System.out.println("Slot number " + slotNo + " is free");
        return slotNo;
    }

    public Map<Slot, Car> status() {
        System.out.println("Slot No.   RegistrationNo      Color");
        for (Map.Entry<Slot, Car> slotCarEntry : slotCarMap.entrySet()) {
            if(slotCarEntry.getValue()!=null) {
                System.out.println(slotCarEntry.getKey().getId() + "           " + slotCarEntry.getValue().getVehicleNo() + "      " + slotCarEntry.getValue().getColor().toString());
            }
        }
        return this.slotCarMap;
    }

    public List<Car> getRegistrationNumbersForCarsWithColour(Color color) {
        List<Car> carList = colorCarListMap.get(color);
        int noOfCars = carList.size();
        StringBuilder carNos = new StringBuilder();
        for (int i = 0; i < noOfCars; i++) {
            carNos.append(carList.get(i).getVehicleNo());
            if (i != (noOfCars - 1)) carNos.append(",");
        }
        System.out.println(carNos.toString());
        return carList;
    }

    public List<Slot> getSlotNumbersForCarsWithColour(Color color) {
        List<Car> carList = colorCarListMap.get(color);
        List<Slot> slots = new ArrayList<Slot>();
        int noOfCars = carList.size();
        StringBuilder slotNos = new StringBuilder();
        for (int i = 0; i < noOfCars; i++) {
            Slot slot = carList.get(i).getSlot();
            slots.add(slot);
            slotNos.append(slot.getId());
            if (i != (noOfCars - 1)) slotNos.append(",");
        }
        System.out.println(slotNos.toString());
        return slots;
    }

    public Slot getSlotNumberForRegistrationNumber(String vehicleNo) throws NoCarFound {
        Slot slot = null;
        try {
            slot =  registrationNumberSlotMap.get(vehicleNo);
            System.out.println(slot.getId());
            return slot;
        } catch (NullPointerException ne) {
            throw  new NoCarFound("Not found");
        }
    }

    private int getFreeSlot() {
        if (freeSlots.size() > 0) {
            return freeSlots.poll();
        } else
            return -1;
    }

    private void addToColorCarListMap(Car car) {
        Color color = car.getColor();
        List<Car> cars = colorCarListMap.get(color);
        if (cars == null) {
            cars = new ArrayList<Car>();
            cars.add(car);
            colorCarListMap.put(color, cars);
        } else {
            cars.add(car);
        }
    }

    private void removeFromColorCarListMap(Car car) {
        Color color = car.getColor();
        List<Car> cars = colorCarListMap.get(color);
        if (cars != null) cars.remove(car);
    }


    private void addToRegistrationNumberSlotMap(Car car, Slot slot) {
        registrationNumberSlotMap.put(car.getVehicleNo(), slot);
    }


    private void removeFromRegistrationNumberSlotMap(Car car) {
        registrationNumberSlotMap.remove(car.getVehicleNo());
    }
}
