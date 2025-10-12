package com.yourcompany.fleet.manager;
import com.yourcompany.fleet.exceptions.InvalidOperationException;
import com.yourcompany.fleet.persistence.FileHandler;
import com.yourcompany.fleet.vehicles.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FleetManager {
    private List<Vehicle> fleet;
    private final FileHandler fileHandler;

    public FleetManager() {
        this.fleet = new ArrayList<>();
        this.fileHandler = new FileHandler();
    }

    public void addVehicle(Vehicle vehicle) throws InvalidOperationException {
        for (Vehicle v : fleet) {
            if (v.getId().equalsIgnoreCase(vehicle.getId())) {
                throw new InvalidOperationException("A vehicle with this ID already exists.");
            }
        }
        fleet.add(vehicle);
    }

    public void removeVehicle(String vehicleId) throws InvalidOperationException {
        Vehicle target = null;
        for (Vehicle v : fleet) {
            if (v.getId().equalsIgnoreCase(vehicleId)) {
                target = v;
                break;
            }
        }
        if (target != null) {
            fleet.remove(target);
        } else {
            throw new InvalidOperationException("Could not find a vehicle with that ID.");
        }
    }

    public void startAllJourneys(double distance) {
        for (Vehicle v : fleet) {
            try {
                v.move(distance);
            } catch (Exception e) {
                System.err.println("Could not move vehicle " + v.getId() + ": " + e.getMessage());
            }
        }
    }

    public String generateReport() {
        if (fleet.isEmpty()) { return "The fleet is empty."; }
        String reportText = "Total Vehicles: " + fleet.size() + "\n---\n";
        for (Vehicle v : fleet) {
            reportText += "ID: " + v.getId() + " (" + v.getClass().getSimpleName() + "), Model: " + v.getModel() + ", Mileage: " + v.getCurrentMileage() + " km\n";
        }
        return reportText;
    }

    public void maintainAll() {
        int count = 0;
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable && ((Maintainable) v).needsMaintenance()) {
                ((Maintainable) v).performMaintenance();
                count++;
            }
        }
        System.out.println("Maintenance complete. Serviced " + count + " vehicle(s).");
    }

    public List<Vehicle> searchByType(Class<?> type) {
        List<Vehicle> found = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (type.isInstance(v)) {
                found.add(v);
            }
        }
        return found;
    }

    public double getTotalFuelConsumption(double distance) {
        double totalFuel = 0.0;
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                double efficiency = v.calculateFuelEfficiency();
                if (efficiency > 0) {
                    totalFuel += (distance / efficiency);
                }
            }
        }
        return totalFuel;
    }

    public List<Vehicle> getVehiclesNeedingMaintenance() {
        List<Vehicle> needsMaintenanceList = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable && ((Maintainable) v).needsMaintenance()) {
                needsMaintenanceList.add(v);
            }
        }
        return needsMaintenanceList;
    }

    public void refuelAllVehicles(double amount) {
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                try {
                    ((FuelConsumable) v).refuel(amount);
                } catch (Exception e) {
                    System.err.println("Could not refuel " + v.getId() + ": " + e.getMessage());
                }
            }
        }
    }

    public void sortFleetByEfficiency() { Collections.sort(fleet); }
    public void saveFleetToFile(String filename) {
        try {
            fileHandler.saveToFile(this.fleet, filename);
            System.out.println("Fleet saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
    public void loadFleetFromFile(String filename) {
        try {
            this.fleet = fileHandler.loadFromFile(filename);
            System.out.println("Fleet loaded from " + filename);
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }
}