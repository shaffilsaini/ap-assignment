package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.*;
public interface CargoCarrier {
    void loadCargo(double weight) throws OverloadException;
    void unloadCargo(double weight) throws InvalidOperationException;
    double getCargoCapacity();
    double getCurrentCargo();
    void setCurrentCargo(double weight);
}