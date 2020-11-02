package by.epam.riverport.ferryboat_state.impl;

import by.epam.riverport.entity.Vehicle;
import by.epam.riverport.entity.Ferryboat;
import by.epam.riverport.ferryboat_state.FerryboatState;
import by.epam.riverport.util.Waiting;

public class FerryboatSailing implements FerryboatState  {
    @Override
    public void perform() {
            for (Vehicle vehicle : Ferryboat.FERRYBOAT.getVehiclesOnTheFerryboat()) {
                vehicle.getVehicleSemaphore().release();
            }
            System.out.println("The Ferryboat \"Fairness\" is sailing on the River of Justice \nfrom port " + Ferryboat.FERRYBOAT.portOfDeparture +
                    " to port " + Ferryboat.FERRYBOAT.portOfArrival + ". " +
                    "\nVehicles on the board: " + Ferryboat.FERRYBOAT.getVehiclesOnTheFerryboat() +
                    "\nВес груза: " + Ferryboat.FERRYBOAT.getCargoWeight() + ". " +
                    "Площадь груза: " + Ferryboat.FERRYBOAT.getOccupiedArea());
            Waiting.wait(2000);

        Ferryboat.FERRYBOAT.setFerryboatState(new FerryboatUnloading());
    }
}
