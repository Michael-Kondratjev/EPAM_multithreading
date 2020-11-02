package by.epam.riverport.util;

import by.epam.riverport.entity.Vehicle;

import java.util.ArrayList;

public class VehiclesGenerator {

    public static void generateVehicles(ArrayList<Vehicle> vehicleList) {
        for (Vehicle vehicle : vehicleList) {
            vehicle.start();
            Waiting.wait(generateTime());
        }
    }

    private static int generateTime() {
        return (int) (Math.random()*3000);
    }

}
