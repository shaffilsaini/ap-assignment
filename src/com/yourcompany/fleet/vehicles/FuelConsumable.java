package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.*;
public interface FuelConsumable {
    void refuel(double amount) throws InvalidOperationException;
    double getFuelLevel();
    void setFuelLevel(double level);
    double consumeFuel(double distance) throws InsufficientFuelException;
}