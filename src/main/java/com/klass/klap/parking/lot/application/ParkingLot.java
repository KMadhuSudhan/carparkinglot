package com.klass.klap.parking.lot.application;

import com.klass.klap.parking.lot.enums.Color;
import com.klass.klap.parking.lot.exceptions.NoCarFound;
import com.klass.klap.parking.lot.exceptions.ParkingNotAvailableException;
import com.klass.klap.parking.lot.models.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        IParkingLot iParkingLot = new parkingLotImpl();
        System.out.println("=========================================================================================");
        System.out.println("***************************** WELCOME TO PARKING LOT CONSOLE ****************************");
        System.out.println("=========================================================================================");
        if (args.length > 0) {
            String fileName = args[0];
            System.out.println("********************* Going to execute the file input*********************");
            processFileInput(fileName, parkingLot, iParkingLot);
        } else {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            while (true) {
                String commandLn = scanner.nextLine().trim();
                parkingLot.processCommand(commandLn, parkingLot, iParkingLot);
            }
        }
    }

    private void printAvailableCommands() {
        System.out.println("Available commands:");
        System.out.println("\t help");
        System.out.println("\t create_parking_lot <slots>");
        System.out.println("\t park <RegistrationNumber> <Color>");
        System.out.println("\t leave <slot>");
        System.out.println("\t status");
        System.out.println("\t registration_numbers_for_cars_with_colour <color>");
        System.out.println("\t slot_numbers_for_cars_with_colour <color>");
        System.out.println("\t slot_number_for_registration_number <registrationNumber>");
    }

    private void processCommand(String commandln, ParkingLot parkingLot, IParkingLot iParkingLot) {
        String[] commandInput = commandln.split(" ");
        String command = commandInput[0].trim();
        Map<String, Integer> commandMapping = mapCommandToNumber();
        try {
            switch (commandMapping.get(command)) {
                case 0:
                    parkingLot.printAvailableCommands();
                    break;
                case 1:
                    if (commandInput.length != 2) {
                        System.out.println("Invalid input");
                    } else {
                        int nrOfSlots = Integer.parseInt(commandInput[1]);
                        iParkingLot.createParkingLot(nrOfSlots);
                    }
                    break;
                case 2:
                    if (commandInput.length != 3) {
                        System.out.println("Invalid input");
                    } else {
                        String vehicleNo = commandInput[1];
                        String color = commandInput[2];
                        Car car = new Car(vehicleNo, Color.valueOf(color.toUpperCase()));
                        try {
                            iParkingLot.park(car);
                        } catch (ParkingNotAvailableException pne) {
                            System.out.println("Sorry, parking lot is full");
                        }
                    }
                    break;
                case 3:
                    if (commandInput.length != 2) {
                        System.out.println("Invalid input");
                    } else {
                        int slotId = Integer.parseInt(commandInput[1]);
                        iParkingLot.leave(slotId);
                    }
                    break;
                case 4:
                    if (commandInput.length != 1) {
                        System.out.println("Invalid input");
                    } else {
                        iParkingLot.status();
                    }
                    break;
                case 5:
                    if (commandInput.length != 2) {
                        System.out.println("Invalid input");
                    } else {
                        String color = commandInput[1];
                        iParkingLot.getRegistrationNumbersForCarsWithColour(Color.valueOf(color.toUpperCase()));
                    }
                    break;
                case 6:
                    if (commandInput.length != 2) {
                        System.out.println("Invalid input");
                    } else {
                        String color = commandInput[1];
                        iParkingLot.getSlotNumbersForCarsWithColour(Color.valueOf(color.toUpperCase()));
                    }
                    break;
                case 7:
                    if (commandInput.length != 2) {
                        System.out.println("Invalid input");
                    } else {
                        String regNo = commandInput[1];
                        try {
                            iParkingLot.getSlotNumberForRegistrationNumber(regNo);
                        } catch (NoCarFound noCarFound) {
                            System.out.println("Not Found");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        } catch (NullPointerException npe) {
            System.out.println("Invalid command");
        }
    }

    private Map<String, Integer> mapCommandToNumber() {
        Map<String, Integer> stringIntegerMap = new HashMap<String, Integer>();
        stringIntegerMap.put("help", 0);
        stringIntegerMap.put("create_parking_lot", 1);
        stringIntegerMap.put("park", 2);
        stringIntegerMap.put("leave", 3);
        stringIntegerMap.put("status", 4);
        stringIntegerMap.put("registration_numbers_for_cars_with_colour", 5);
        stringIntegerMap.put("slot_numbers_for_cars_with_colour", 6);
        stringIntegerMap.put("slot_number_for_registration_number", 7);
        return stringIntegerMap;
    }

    private static void processFileInput(String fileName, ParkingLot parkingLot, IParkingLot iParkingLot) {
        BufferedReader br = null;
        FileReader fr = null;
        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                parkingLot.processCommand(sCurrentLine, parkingLot, iParkingLot);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }
}
