package com.yourcompany.fleet.persistence;
import com.yourcompany.fleet.factory.VehicleFactory;
import com.yourcompany.fleet.vehicles.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public void saveToFile(List<Vehicle> fleet, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Vehicle vehicle : fleet) {
                String line = vehicleToCsvString(vehicle);
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public List<Vehicle> loadFromFile(String filename) throws IOException {
        List<Vehicle> fleet = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    String type = data[0];
                    String[] constructorData;
                    int stateIndex;

                    if (type.equalsIgnoreCase("Airplane") || type.equalsIgnoreCase("CargoShip")) {
                        constructorData = new String[]{data[1], data[2], data[3], data[4]};
                        stateIndex = 5;
                    } else {
                        constructorData = new String[]{data[1], data[2], data[3]};
                        stateIndex = 4;
                    }

                    Vehicle vehicle = VehicleFactory.createVehicle(type, constructorData);

                    if (data.length > stateIndex) vehicle.setCurrentMileage(Double.parseDouble(data[stateIndex]));
                    if (data.length > stateIndex + 1 && vehicle instanceof FuelConsumable) ((FuelConsumable) vehicle).setFuelLevel(Double.parseDouble(data[stateIndex + 1]));
                    if (data.length > stateIndex + 2 && vehicle instanceof PassengerCarrier) ((PassengerCarrier) vehicle).setCurrentPassengers(Integer.parseInt(data[stateIndex + 2]));
                    if (data.length > stateIndex + 3 && vehicle instanceof CargoCarrier) ((CargoCarrier) vehicle).setCurrentCargo(Double.parseDouble(data[stateIndex + 3]));
                    if (data.length > stateIndex + 4 && vehicle instanceof Maintainable) ((Maintainable) vehicle).setMaintenanceStatus(Boolean.parseBoolean(data[stateIndex + 4]));

                    fleet.add(vehicle);
                } catch (Exception e) {
                    System.err.println("Skipping invalid line in file: " + line);
                }
            }
        }
        return fleet;
    }

    private String vehicleToCsvString(Vehicle vehicle) {
        List<String> parts = new ArrayList<>();
        parts.add(vehicle.getClass().getSimpleName());
        parts.add(vehicle.getId());
        parts.add(vehicle.getModel());
        parts.add(String.valueOf(vehicle.getMaxSpeed()));
        if (vehicle instanceof Airplane) parts.add(String.valueOf(((Airplane) vehicle).getMaxAltitude()));
        if (vehicle instanceof CargoShip) parts.add(String.valueOf(((CargoShip) vehicle).hasSail()));
        parts.add(String.valueOf(vehicle.getCurrentMileage()));
        if (vehicle instanceof FuelConsumable) parts.add(String.valueOf(((FuelConsumable) vehicle).getFuelLevel())); else parts.add("0.0");
        if (vehicle instanceof PassengerCarrier) parts.add(String.valueOf(((PassengerCarrier) vehicle).getCurrentPassengers())); else parts.add("0");
        if (vehicle instanceof CargoCarrier) parts.add(String.valueOf(((CargoCarrier) vehicle).getCurrentCargo())); else parts.add("0.0");
        if (vehicle instanceof Maintainable) parts.add(String.valueOf(((Maintainable) vehicle).needsMaintenance())); else parts.add("false");
        return String.join(",", parts);
    }
}