package com.yourcompany.fleet.factory;
import com.yourcompany.fleet.exceptions.InvalidOperationException;
import com.yourcompany.fleet.vehicles.*;

public class VehicleFactory {
    public static Vehicle createVehicle(String type, String... data) throws InvalidOperationException {
        try {
            String id = data[0];
            String model = data[1];
            double maxSpeed = Double.parseDouble(data[2]);
            if (type.equalsIgnoreCase("car")) {
                return new Car(id, model, maxSpeed);
            } else if (type.equalsIgnoreCase("truck")) {
                return new Truck(id, model, maxSpeed);
            } else if (type.equalsIgnoreCase("bus")) {
                return new Bus(id, model, maxSpeed);
            } else if (type.equalsIgnoreCase("airplane")) {
                double maxAltitude = Double.parseDouble(data[3]);
                return new Airplane(id, model, maxSpeed, maxAltitude);
            } else if (type.equalsIgnoreCase("cargoship")) {
                boolean sail = data[3].equalsIgnoreCase("true");
                return new CargoShip(id, model, maxSpeed, sail);
            } else {
                throw new InvalidOperationException("Unknown type: " + type);
            }
        } catch (Exception e) {
            throw new InvalidOperationException("Invalid data");
        }
    }
}