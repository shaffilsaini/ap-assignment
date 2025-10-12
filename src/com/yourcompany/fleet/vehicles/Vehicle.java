package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.InvalidOperationException;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String id;
    private String model;
    private double maxSpeed;
    protected double currentMileage;

    public Vehicle(String id, String model, double maxSpeed) throws InvalidOperationException {
        if (id == null || id.trim().isEmpty()) { throw new InvalidOperationException("Vehicle ID cannot be empty."); }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    public abstract void move(double distance) throws InvalidOperationException;
    public abstract double calculateFuelEfficiency();
    public abstract double estimateJourneyTime(double distance);

    public void displayInfo() {
        System.out.printf("ID: %s, Model: %s, Max Speed: %.2f km/h, Mileage: %.2f km\n", id, model, maxSpeed, currentMileage);
    }

    protected void addMileage(double distance) {
        if (distance > 0) this.currentMileage += distance;
    }

    public double getCurrentMileage() { return currentMileage; }
    public String getId() { return id; }
    public String getModel() { return model; }
    public double getMaxSpeed() { return maxSpeed; }

    public void setCurrentMileage(double mileage) {
        this.currentMileage = mileage;
    }

    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(other.calculateFuelEfficiency(), this.calculateFuelEfficiency());
    }
}