package by.epam.riverport.reader;

import by.epam.riverport.entity.Vehicle;
import by.epam.riverport.exception.DataException;
import by.epam.riverport.parser.VehicleParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class VehicleReader {

    private static final Logger logger = LogManager.getLogger();

    public ArrayList<Vehicle> read(String filename) throws DataException {
        VehicleParser vehicleParser = new VehicleParser();
        ArrayList<Vehicle> vehicleList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    lineNumber++;
                    Vehicle vehicle = vehicleParser.parseLineToVehicle(line);
                    vehicleList.add(vehicle);
                } catch (DataException e) {
                    logger.log(Level.INFO, "Vehicle cannot be created.\n" +
                            "File " + filename + " Line № " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new DataException("Error reading file " + filename, e);
        }

        if (vehicleList.isEmpty()) {
            throw new DataException("File " + filename + " don't contain correct data.");
        }

        return vehicleList;
    }
}
