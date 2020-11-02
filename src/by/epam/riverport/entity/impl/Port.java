package by.epam.riverport.entity.impl;

import by.epam.riverport.entity.IPort;
import by.epam.riverport.entity.Vehicle;
import by.epam.riverport.entity.Ferryboat;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public enum  Port implements IPort {
    FREE_BELARUS, PRISON;

    public Semaphore semaphore = new Semaphore(0);
    private final ConcurrentLinkedQueue<Vehicle> portQueue = new ConcurrentLinkedQueue<>();
    public Thread thread = new Thread(this);
    public static boolean bothPortsAreEmpty = false;

    @Override
    public void loadVehiclesOnTheFerryboat() {
        System.out.println("Началась погрузка в порту " + name());
        while (! portQueue.isEmpty()) {
            Vehicle vehicleFromQ = portQueue.peek();
            if(vehicleFromQ.permissibleVehicleParameters()) {
                if (Ferryboat.FERRYBOAT.freeSpaceOnFerryboat(vehicleFromQ)) {
                    Ferryboat.FERRYBOAT.loadThisVehicleOnFerryboat(vehicleFromQ);
                    portQueue.remove(vehicleFromQ);
                } else {
                    return;
                }
            } else {
                portQueue.remove(vehicleFromQ);
                vehicleFromQ.getVehicleSemaphore().release();

            }
        }
            System.out.println("Погрузка в порту " + name() + " окончена.");
    }

    @Override
    public void addIntoTheQueue(Vehicle vehicle) {
        portQueue.add(vehicle);
        System.out.println("В очереди порта " + name() + portQueue);
    }

    @Override
    public boolean portIsEmpty() {
        return portQueue.isEmpty();
    }

    @Override
    public void run() {
        System.out.println("Port " + this + " is run");
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (Ferryboat.isWorking) {
            loadVehiclesOnTheFerryboat();
            Ferryboat.FERRYBOAT.ferryboatSemaphore.release();
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Port " + name() + " is over");
    }

    @Override
    public String toString() {
        if (this == FREE_BELARUS) {
            return "\"Free Belarus\"";
        } else {
            return "\"Prison\"";
        }
    }
}


