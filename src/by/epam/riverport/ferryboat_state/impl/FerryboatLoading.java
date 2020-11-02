package by.epam.riverport.ferryboat_state.impl;

import by.epam.riverport.entity.Ferryboat;
import by.epam.riverport.ferryboat_state.FerryboatState;

public class FerryboatLoading implements FerryboatState {

    @Override
    public void perform() {
        System.out.println("Паром ожидает окончания погрузки.");
        Ferryboat.FERRYBOAT.portOfDeparture.semaphore.release();
        try {
            Ferryboat.FERRYBOAT.ferryboatSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Погрузка окончена");

        Ferryboat.FERRYBOAT.setFerryboatState(new FerryboatSailing());
    }

}
