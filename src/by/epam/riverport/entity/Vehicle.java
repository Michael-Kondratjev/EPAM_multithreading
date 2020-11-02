package by.epam.riverport.entity;

import by.epam.riverport.entity.impl.Port;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Vehicle extends Thread {

    private final double weight;
    private final double area;
    private final IPort portOfDeparture;
    private final IPort portOfArrival;
    private final Semaphore vehicleSemaphore;

    public Vehicle(boolean yourSideIs, String name, double weight, double area) {
        super(name);
        this.weight = weight;
        this.area = area;
        vehicleSemaphore = new Semaphore(0);
        if (yourSideIs) {
            portOfDeparture = Port.PRISON;
            portOfArrival = Port.FREE_BELARUS;
        } else {
            portOfDeparture = Port.FREE_BELARUS;
            portOfArrival = Port.PRISON;
        }
    }

    @Override
    public void run() {
        System.out.println("Создано ТС: " + this);
        moveToThePort();
        portOfDeparture.addIntoTheQueue(this);
        try {
            vehicleSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (permissibleVehicleParameters()) {
            System.out.println(super.getName() + " плывет на пароме в порт " + portOfArrival);
            try {
                vehicleSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(super.getName() + " прибыл в порт " + portOfArrival);
        } else {
            System.out.println(super.getName() + " не может быть переправлен в порт " + portOfArrival);
        }
    }

    private void moveToThePort() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Semaphore getVehicleSemaphore() {
        return vehicleSemaphore;
    }
    public double getWeight() { return weight; }
    public double getArea() { return area; }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append(super.getName()).
                append(" [weight=").
                append(weight).
                append(", area=").
                append(area).
                append("]");

        return stringBuilder.toString();
    }

    public boolean permissibleVehicleParameters() {
        return getWeight() <= Ferryboat.LIFTING_CAPACITY && getArea() <= Ferryboat.MAX_AREA;
    }
}
