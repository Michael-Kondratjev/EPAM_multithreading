package by.epam.riverport.ferryboat_state.impl;

import by.epam.riverport.entity.Vehicle;
import by.epam.riverport.entity.impl.Port;
import by.epam.riverport.entity.Ferryboat;
import by.epam.riverport.ferryboat_state.FerryboatState;
import by.epam.riverport.util.Waiting;

public class FerryboatUnloading implements FerryboatState {
    @Override
    public void perform() {
        for (Vehicle vehicle : Ferryboat.FERRYBOAT.getVehiclesOnTheFerryboat()) {
            vehicle.getVehicleSemaphore().release();
        }
        Ferryboat.FERRYBOAT.getVehiclesOnTheFerryboat().clear();
        Waiting.wait(500);
        Ferryboat.FERRYBOAT.setCargoWeight(0);
        Ferryboat.FERRYBOAT.setOccupiedArea(0);
        System.out.println("Паром разгрузился в порту " + Ferryboat.FERRYBOAT.portOfArrival);

        changeDestination();
        checkPorts();

        Ferryboat.FERRYBOAT.setFerryboatState(new FerryboatLoading());
    }

    private void changeDestination() {
        Port port = Ferryboat.FERRYBOAT.portOfArrival;
        Ferryboat.FERRYBOAT.portOfArrival = Ferryboat.FERRYBOAT.portOfDeparture;
        Ferryboat.FERRYBOAT.portOfDeparture = port;
        System.out.println("Направление изменено");
    }

    private void checkPorts() {
        if (Ferryboat.FERRYBOAT.portOfDeparture.portIsEmpty()) {
            Waiting.wait(3000);
            if (Ferryboat.FERRYBOAT.portOfDeparture.portIsEmpty()) {
                if (Ferryboat.FERRYBOAT.portOfArrival.portIsEmpty()) {
                    Port.bothPortsAreEmpty = true;
                }
            }
        }
    }
}
