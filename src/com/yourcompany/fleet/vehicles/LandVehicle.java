package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.InvalidOperationException;

public abstract class LandVehicle extends Vehicle {
    private int numWheels;
    public LandVehicle(String id, String model, double maxSpeed, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed);
        this.numWheels = numWheels;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        return (distance / getMaxSpeed()) * 1.10;
    }
}