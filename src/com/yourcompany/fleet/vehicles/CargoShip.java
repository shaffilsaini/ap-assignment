package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.*;

public class CargoShip extends WaterVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    private double fuelLevel;
    private final double cargoCapacity = 50000.0;
    private double currentCargo;
    private boolean maintenanceNeeded;
    public CargoShip(String id, String model, double maxSpeed, boolean hasSail) throws InvalidOperationException { super(id, model, maxSpeed, hasSail); }

    @Override
    public void move(double distance) throws InvalidOperationException {
        if (distance <= 0) throw new InvalidOperationException("Distance must be positive.");
        try {
            if (!hasSail()) { consumeFuel(distance); }
            addMileage(distance);
            System.out.println("Sailing with cargo...");
        } catch (InsufficientFuelException e) {
            System.err.println("Failed to move CargoShip " + getId() + ": " + e.getMessage());
        }
    }

    @Override
    public double calculateFuelEfficiency() { return hasSail() ? 0 : 4.0; }
    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (hasSail()) throw new InvalidOperationException("A sailboat cannot be refueled.");
        if (amount <= 0) throw new InvalidOperationException("Refuel amount must be positive.");
        this.fuelLevel += amount;
    }
    @Override
    public double getFuelLevel() { return fuelLevel; }
    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        if (hasSail()) return 0;
        double fuel = distance / calculateFuelEfficiency();
        if (fuelLevel < fuel) { throw new InsufficientFuelException("Not enough fuel."); }
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
    public void performMaintenance() { this.maintenanceNeeded = false; System.out.println("Maintenance performed on CargoShip " + getId() + "."); }

    @Override
    public void setFuelLevel(double level) { this.fuelLevel = level; }
    @Override
    public void setCurrentCargo(double weight) { this.currentCargo = weight; }
    @Override
    public void setMaintenanceStatus(boolean status) { this.maintenanceNeeded = status; }
}