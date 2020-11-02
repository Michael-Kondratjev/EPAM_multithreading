package by.epam.riverport.entity;

import by.epam.riverport.entity.impl.Port;
import by.epam.riverport.ferryboat_state.FerryboatState;
import by.epam.riverport.ferryboat_state.impl.FerryboatLoading;
import by.epam.riverport.util.Waiting;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public enum Ferryboat implements Runnable {
    FERRYBOAT;

    public Port portOfDeparture = Port.PRISON;
    public Port portOfArrival = Port.FREE_BELARUS;
    public static final int LIFTING_CAPACITY = 15000;
    private double cargoWeight;
    public static final int MAX_AREA = 80;
    private double occupiedArea;
    public static boolean isWorking = true;
    private final ArrayList<Vehicle> vehiclesOnTheFerryboat = new ArrayList<>();
    public Semaphore ferryboatSemaphore = new Semaphore(0);
    public Thread thread = new Thread(this);
    private FerryboatState ferryboatState = new FerryboatLoading();

    public void setFerryboatState(FerryboatState ferryboatState) {
        this.ferryboatState = ferryboatState;
    }

    public boolean freeSpaceOnFerryboat(Vehicle vehicleForLoading) {
        return cargoWeight + vehicleForLoading.getWeight() <= LIFTING_CAPACITY &&
                occupiedArea + vehicleForLoading.getArea() <= MAX_AREA;
    }

    public void loadThisVehicleOnFerryboat(Vehicle vehicle) {
        Ferryboat.FERRYBOAT.vehiclesOnTheFerryboat.add(vehicle);
        Ferryboat.FERRYBOAT.cargoWeight += vehicle.getWeight();
        Ferryboat.FERRYBOAT.occupiedArea += vehicle.getArea();
    }

    public double getCargoWeight() {
        return cargoWeight;
    }
    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public double getOccupiedArea() {
        return occupiedArea;
    }
    public void setOccupiedArea(double occupiedArea) {
        this.occupiedArea = occupiedArea;
    }

    public ArrayList<Vehicle> getVehiclesOnTheFerryboat() {
        return vehiclesOnTheFerryboat;
    }

    @Override
    public void run() {
        System.out.println("Ferryboat is run");
        Waiting.wait(2000);
        while (! Port.bothPortsAreEmpty) {
            ferryboatState.perform();
        }
        isWorking = false;
        portOfDeparture.semaphore.release();
        portOfArrival.semaphore.release();
        System.out.println("Ferryboat is over");
    }
}
