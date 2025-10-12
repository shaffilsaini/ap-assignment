package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.InvalidOperationException;

public abstract class AirVehicle extends Vehicle {
    private double maxAltitude;
    public AirVehicle(String id, String model, double maxSpeed, double maxAltitude) throws InvalidOperationException {
        super(id, model, maxSpeed);
        this.maxAltitude = maxAltitude;
    }
    public double getMaxAltitude() { return maxAltitude; }


    @Override
    public double estimateJourneyTime(double distance) {
        return (distance / getMaxSpeed()) * 0.95;
    }
}