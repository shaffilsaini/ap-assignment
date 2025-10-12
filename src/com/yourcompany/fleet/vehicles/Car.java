package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.*;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable {
    private double fuelLevel;
    private final int passengerCapacity = 5;
    private int currentPassengers;
    private boolean maintenanceNeeded;
    public Car(String id, String model, double maxSpeed) throws InvalidOperationException { super(id, model, maxSpeed, 4); }

    @Override
    public void move(double distance) throws InvalidOperationException {
        if (distance <= 0) throw new InvalidOperationException("Distance must be positive.");
        try {
            consumeFuel(distance);
            addMileage(distance);
            System.out.println("Driving on road...");
        } catch (InsufficientFuelException e) {
            System.err.println("Failed to move Car " + getId() + ": " + e.getMessage());
        }
    }

    @Override
    public double calculateFuelEfficiency() { return 15.0; }
    @Override
    public void refuel(double amount) throws InvalidOperationException { if (amount <= 0) throw new InvalidOperationException("Refuel amount must be positive."); this.fuelLevel += amount; }
    @Override
    public double getFuelLevel() { return fuelLevel; }
    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double fuel = distance / calculateFuelEfficiency();
        if (fuelLevel < fuel) { throw new InsufficientFuelException("Not enough fuel for the journey."); }
        this.fuelLevel -= fuel;
        return fuel;
    }
    @Override
    public void boardPassengers(int count) throws OverloadException { if (currentPassengers + count > passengerCapacity) { throw new OverloadException("Passenger capacity exceeded."); } this.currentPassengers += count; }
    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException { if (count > currentPassengers) { throw new InvalidOperationException("Cannot disembark more passengers than are on board."); } this.currentPassengers -= count; }
    @Override
    public int getPassengerCapacity() { return passengerCapacity; }
    @Override
    public int getCurrentPassengers() { return currentPassengers; }
    @Override
    public void scheduleMaintenance() { this.maintenanceNeeded = true; }
    @Override
    public boolean needsMaintenance() { return maintenanceNeeded || getCurrentMileage() > 10000; }
    @Override
    public void performMaintenance() { this.maintenanceNeeded = false; System.out.println("Maintenance performed on Car " + getId() + "."); }

    @Override
    public void setFuelLevel(double level) { this.fuelLevel = level; }
    @Override
    public void setCurrentPassengers(int count) { this.currentPassengers = count; }
    @Override
    public void setMaintenanceStatus(boolean status) { this.maintenanceNeeded = status; }
}