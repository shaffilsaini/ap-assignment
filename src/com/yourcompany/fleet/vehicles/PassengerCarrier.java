package com.yourcompany.fleet.vehicles;
import com.yourcompany.fleet.exceptions.*;
public interface PassengerCarrier {
    void boardPassengers(int count) throws OverloadException;
    void disembarkPassengers(int count) throws InvalidOperationException;
    int getPassengerCapacity();
    int getCurrentPassengers();
    void setCurrentPassengers(int count);
}