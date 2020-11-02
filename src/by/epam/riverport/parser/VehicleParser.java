package by.epam.riverport.parser;

import by.epam.riverport.entity.Vehicle;
import by.epam.riverport.exception.DataException;

public class VehicleParser {

    private final String DELIMITER = " +";

    public Vehicle parseLineToVehicle(String parameters) throws DataException {
        parameters = parameters.trim();
        if (parameters.isEmpty()) {
            throw new DataException(" don't contain data.");
        }
        String[] dataArray = parameters.split(DELIMITER);

        if (dataArray.length != 4) {
            throw new DataException(" wrong number of data.");
        }

        double weight;
        double area;
        try {
            weight = Double.parseDouble(dataArray[2]);
            area = Double.parseDouble(dataArray[3]);
        } catch (NumberFormatException e) {
            throw new DataException(" wrong format of vehicle weight or area.");
        }

        if (weight <=0 || area <= 0) {
            throw new DataException(" incorrect data: weight or area must be greater than zero");
        }

        String name = dataArray[1];
        boolean yourSideIs = false;
        if (dataArray[0].equals("true")) {
            yourSideIs = true;
        }

        return new Vehicle(yourSideIs,name,weight,area);
    }
}
