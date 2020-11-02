package by.epam.riverport.entity;

public interface IPort extends Runnable {
    void loadVehiclesOnTheFerryboat();
    void addIntoTheQueue(Vehicle vehicle);
    boolean portIsEmpty();
}
