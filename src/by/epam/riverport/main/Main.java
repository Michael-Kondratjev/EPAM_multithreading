package by.epam.riverport.main;

import by.epam.riverport.entity.Vehicle;
import by.epam.riverport.entity.impl.Port;
import by.epam.riverport.exception.DataException;
import by.epam.riverport.exception.RiverPortException;
import by.epam.riverport.entity.Ferryboat;
import by.epam.riverport.reader.VehicleReader;
import by.epam.riverport.util.VehiclesGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws RiverPortException {
        VehicleReader vehicleReader = new VehicleReader();
        ArrayList<Vehicle> vehicleList;
        try {
            vehicleList = vehicleReader.read("resources/SeveralVehicles.txt");
        } catch (DataException e) {
            logger.log(Level.FATAL, "The program cannot be started. Cause: " + e.getMessage());
            throw new RiverPortException("The program cannot be started. Cause: " + e.getMessage(), e);
        }

        Ferryboat.FERRYBOAT.thread.start();
        Port.FREE_BELARUS.thread.start();
        Port.PRISON.thread.start();

        VehiclesGenerator.generateVehicles(vehicleList);

    }
}
