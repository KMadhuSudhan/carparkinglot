package com.klass.klap.praking.lot;

import com.klass.klap.parking.lot.application.IParkingLot;
import com.klass.klap.parking.lot.application.parkingLotImpl;
import com.klass.klap.parking.lot.enums.Color;
import com.klass.klap.parking.lot.exceptions.NoCarFound;
import com.klass.klap.parking.lot.exceptions.ParkingNotAvailableException;
import com.klass.klap.parking.lot.models.Car;
import com.klass.klap.parking.lot.models.Slot;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParkingLotIntegrationTest {
    static IParkingLot parkingLot;
    static int numSlots = 6;


    @BeforeClass
    public static void setup() {
        parkingLot = new parkingLotImpl();
    }

    @Test
    public void testPark() throws Exception {
        parkingLot.createParkingLot(6);
        System.out.println("Created a parking lot with " + numSlots + " slots");

        int slotId = parkingLot.park(new Car("KA-01-HH-1234", Color.WHITE));
        assertEquals(1, slotId);

        slotId = parkingLot.park(new Car("KA-01-HH-9999", Color.WHITE));
        assertEquals(2, slotId);

        slotId = parkingLot.park(new Car("KA-01-BB-0001", Color.BLACK));
        assertEquals(3, slotId);

        slotId = parkingLot.park(new Car("KA-01-HH-7777", Color.RED));
        assertEquals(4, slotId);

        slotId = parkingLot.park(new Car("KA-01-HH-2701", Color.BLACK));
        assertEquals(5, slotId);

        slotId = parkingLot.park(new Car("KA-01-HH-3141", Color.BLACK));
        assertEquals(6, slotId);
    }

    @Test
    public void testStatus() {
        int slotId = parkingLot.leave(4);
        assertEquals(4, slotId);
        parkingLot.status();
        try {
            parkingLot.park(new Car("KA-01-P-333", Color.WHITE));
            Map<Slot, Car> slotCarMap = parkingLot.status();
            assertEquals(6, slotCarMap.size());
        } catch (ParkingNotAvailableException e) {
            e.printStackTrace();

        }

        try {
            slotId = parkingLot.park(new Car("DL-12-AA-9999", Color.WHITE));
        } catch (ParkingNotAvailableException e) {
        }

        List<Car> whiteCars = parkingLot.getRegistrationNumbersForCarsWithColour(Color.WHITE);
        assertEquals(3, whiteCars.size());
        List<String> vehicleNos = new ArrayList<String>();
        for (Car car : whiteCars) {
            vehicleNos.add(car.getVehicleNo());
        }
        assertEquals(true, vehicleNos.contains("KA-01-HH-1234"));
        assertEquals(true, vehicleNos.contains("KA-01-HH-9999"));
        assertEquals(true, vehicleNos.contains("KA-01-P-333"));

        for (String regNum : vehicleNos) {
            System.out.print(regNum + ",");
        }

        List<Slot> whiteSlots = parkingLot.getSlotNumbersForCarsWithColour(Color.WHITE);
        assertEquals(3, whiteSlots.size());
        for (Slot s : whiteSlots) {
            System.out.print(s.getId() + ",");
        }
        try {
            assertEquals(6, parkingLot.getSlotNumberForRegistrationNumber("KA-01-HH-3141"));
        } catch (NoCarFound noCarFound) {

        }
        try {
            Slot slot = parkingLot.getSlotNumberForRegistrationNumber("MH-04-AY-1111");
            assertEquals(null, slot);
        } catch (NoCarFound noCarFound) {
        }
    }

    @Test
    public void testLeave() {
        parkingLot.createParkingLot(10);
        for (int c = 1; c <= 10; c++) {
            try {
                parkingLot.park(new Car("KA-10-ME-" + c, Color.WHITE));

            } catch (ParkingNotAvailableException e) {
                e.printStackTrace();
            }
        }

        try {
            parkingLot.park(new Car("KA-10-ME-" + 11, Color.WHITE));

        } catch (Exception e) {
            assertTrue(e instanceof ParkingNotAvailableException);

        }
        parkingLot.leave(5);
        parkingLot.leave(1);
        parkingLot.leave(3);

        //Now park 3 cars
        try {
            int slotId = slotId = parkingLot.park(new Car("KA-10-ME-" + 1, Color.WHITE));
            assertEquals(1, slotId);

            slotId = parkingLot.park(new Car("KA-10-ME-" + 3, Color.WHITE));
            assertEquals(3, slotId);

            slotId = parkingLot.park(new Car("KA-10-ME-" + 5, Color.WHITE));
            assertEquals(5, slotId);
        } catch (ParkingNotAvailableException e) {
            e.printStackTrace();

        }
    }

    @Test
    public void testGetCarsByColor() {
        parkingLot.createParkingLot(15);

        for (int c = 1; c <= 10; c++) {
            try {
                parkingLot.park(new Car("KA-10-ME-" + c, Color.WHITE));
            } catch (ParkingNotAvailableException e) {
                e.printStackTrace();

            }
        }
        for (int c = 11; c <= 15; c++) {
            try {
                parkingLot.park(new Car("KA-10-ME-" + c, Color.BLACK));
            } catch (ParkingNotAvailableException e) {
                e.printStackTrace();

            }
        }
        List<Car> whiteCars = parkingLot.getRegistrationNumbersForCarsWithColour(Color.WHITE);
        assertEquals(10, whiteCars.size());

        List<Car> blackCars = parkingLot.getRegistrationNumbersForCarsWithColour(Color.BLACK);
        assertEquals(5, blackCars.size());
    }

    @Test
    public void testGetSlots() {
        parkingLot.createParkingLot(5);

        for (int c = 1; c <= 5; c++) {
            try {
                parkingLot.park(new Car("KA-10-ME-" + c, Color.WHITE));
            } catch (ParkingNotAvailableException e) {
                e.printStackTrace();

            }
        }
        List<Slot> whiteSlots = parkingLot.getSlotNumbersForCarsWithColour(Color.WHITE);
        assertEquals(5, whiteSlots.size());

        assertTrue(whiteSlots.contains(new Slot(1)));
        assertTrue(whiteSlots.contains(new Slot(2)));
        assertTrue(whiteSlots.contains(new Slot(3)));
        assertTrue(whiteSlots.contains(new Slot(4)));
        assertTrue(whiteSlots.contains(new Slot(5)));

        assertFalse(whiteSlots.contains(new Slot(10)));
    }
}
