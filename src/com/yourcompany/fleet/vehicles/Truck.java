package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.*;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    private double fuelLevel;
    private final double cargoCapacity = 5000.0;
    private double currentCargo;
    private boolean maintenanceNeeded;
    public Truck(String id, String model, double maxSpeed) throws InvalidOperationException { super(id, model, maxSpeed, 6); }

    @Override
    public void move(double distance) throws InvalidOperationException {
        if (distance <= 0) throw new InvalidOperationException("Distance must be positive.");
        try {
            consumeFuel(distance);
            addMileage(distance);
            System.out.println("Hauling cargo...");
        } catch (InsufficientFuelException e) {
            System.err.println("Failed to move Truck " + getId() + ": " + e.getMessage());
        }
    }

    // Other methods...
    @Override
    public double calculateFuelEfficiency() { if (currentCargo > cargoCapacity * 0.5) { return 8.0 * 0.9; } return 8.0; }
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
    public void loadCargo(double weight) throws OverloadException { if (currentCargo + weight > cargoCapacity) { throw new OverloadException("Cargo capacity exceeded."); } this.currentCargo += weight; }
    @Override
    public void unloadCargo(double weight) throws InvalidOperationException { if (weight > currentCargo) { throw new InvalidOperationException("Cannot unload more cargo than is loaded."); } this.currentCargo -= weight; }
    @Override
    public double getCargoCapacity() { return cargoCapacity; }
    @Override
    public double getCurrentCargo() { return currentCargo; }
    @Override
    public void scheduleMaintenance() { this.maintenanceNeeded = true; }
    @Override
    public boolean needsMaintenance() { return maintenanceNeeded || getCurrentMileage() > 10000; }
    @Override
    public void performMaintenance() { this.maintenanceNeeded = false; System.out.println("Maintenance performed on Truck " + getId() + "."); }

    @Override
    public void setFuelLevel(double level) { this.fuelLevel = level; }
    @Override
    public void setCurrentCargo(double weight) { this.currentCargo = weight; }
    @Override
    public void setMaintenanceStatus(boolean status) { this.maintenanceNeeded = status; }
}