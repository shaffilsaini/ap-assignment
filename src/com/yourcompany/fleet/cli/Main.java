package com.yourcompany.fleet.cli;
import com.yourcompany.fleet.factory.VehicleFactory;
import com.yourcompany.fleet.manager.FleetManager;
import com.yourcompany.fleet.vehicles.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final FleetManager manager = new FleetManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initDemo();
        boolean running = true;
        while (running) {
            showMenu();
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {

            }
            scanner.nextLine();

            if (choice == 1) { addVehicle(); }
            else if (choice == 2) { removeVehicle(); }
            else if (choice == 3) { startJourney(); }
            else if (choice == 4) { refuel(); }
            else if (choice == 5) { manager.maintainAll(); }
            else if (choice == 6) { System.out.println(manager.generateReport()); }
            else if (choice == 7) { save(); }
            else if (choice == 8) { load(); }
            else if (choice == 9) { search(); }
            else if (choice == 10) { listMaintenance(); }
            else if (choice == 11) { running = false; }
            else { System.err.println("Invalid choice."); }
        }
        System.out.println("Exiting.");
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n1. Add Vehicle\n2. Remove Vehicle\n3. Start Journey\n4. Refuel All\n5. Perform Maintenance\n6. Generate Report\n7. Save Fleet\n8. Load Fleet\n9. Search by Type\n10. List Maintenance Needs\n11. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addVehicle() {
        try {
            System.out.print("Enter type (Car, Truck, Bus, Airplane, CargoShip): ");
            String type = scanner.nextLine();
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Model: ");
            String model = scanner.nextLine();
            System.out.print("Enter Max Speed: ");
            String maxSpeed = scanner.nextLine();
            Vehicle v;
            if (type.equalsIgnoreCase("Airplane")) {
                System.out.print("Enter Max Altitude: ");
                String maxAltitude = scanner.nextLine();
                v = VehicleFactory.createVehicle(type, id, model, maxSpeed, maxAltitude);
            } else if (type.equalsIgnoreCase("CargoShip")) {
                System.out.print("Has sail? (true/false): ");
                String hasSail = scanner.nextLine();
                v = VehicleFactory.createVehicle(type, id, model, maxSpeed, hasSail);
            } else {
                v = VehicleFactory.createVehicle(type, id, model, maxSpeed);
            }
            manager.addVehicle(v);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void removeVehicle() {
        try {
            System.out.print("Enter vehicle ID to remove: ");
            String id = scanner.nextLine();
            manager.removeVehicle(id);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void startJourney() {
        try {
            System.out.print("Enter journey distance: ");
            double distance = scanner.nextDouble();
            scanner.nextLine();
            manager.startAllJourneys(distance);
        } catch (InputMismatchException e) {
            System.err.println("Invalid distance.");
            scanner.nextLine();
        }
    }

    private static void refuel() {
        try {
            System.out.print("Enter fuel amount to add: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            manager.refuelAllVehicles(amount);
        } catch (InputMismatchException e) {
            System.err.println("Invalid amount.");
            scanner.nextLine();
        }
    }

    private static void save() {
        System.out.print("Enter filename (default: fleet.csv): ");
        String filename = scanner.nextLine();
        if (filename.isEmpty()) filename = "fleet.csv";
        manager.saveFleetToFile(filename);
    }

    private static void load() {
        System.out.print("Enter filename (default: fleet.csv): ");
        String filename = scanner.nextLine();
        if (filename.isEmpty()) filename = "fleet.csv";
        manager.loadFleetFromFile(filename);
    }

    private static void search() {
        System.out.print("Enter type to search (e.g. Car, FuelConsumable): ");
        String typeName = scanner.nextLine();
        try {
            Class<?> type = Class.forName("com.yourcompany.fleet.vehicles." + typeName);
            List<Vehicle> results = manager.searchByType(type);
            System.out.println("Found " + results.size() + " vehicle(s).");
            for(Vehicle v : results) { v.displayInfo(); }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Type not found.");
        }
    }
    int a=1;
    int b= a+1;
    private static void listMaintenance() {
        List<Vehicle> results = manager.getVehiclesNeedingMaintenance();
        System.out.println(results.size() + " vehicle(s) need maintenance.");
        for(Vehicle v : results) { v.displayInfo(); }
    }

    private static void initDemo() {
        System.out.println("Initializing demo fleet with 2 vehicles of each type...");
        try {
            manager.addVehicle(VehicleFactory.createVehicle("Car", "C001", "Honda Civic", "190"));
            manager.addVehicle(VehicleFactory.createVehicle("Car", "C002", "Ford Mustang", "250"));

            manager.addVehicle(VehicleFactory.createVehicle("Truck", "T001", "Scania R 730", "135"));
            manager.addVehicle(VehicleFactory.createVehicle("Truck", "T002", "Kenworth W900", "145"));

            manager.addVehicle(VehicleFactory.createVehicle("Bus", "B001", "Mercedes-Benz Tourismo", "120"));
            manager.addVehicle(VehicleFactory.createVehicle("Bus", "B002", "Volvo 9700", "125"));

            manager.addVehicle(VehicleFactory.createVehicle("Airplane", "A001", "Airbus A380", "1020", "43000"));
            manager.addVehicle(VehicleFactory.createVehicle("Airplane", "A002", "Cessna 172", "226", "14000"));

            manager.addVehicle(VehicleFactory.createVehicle("CargoShip", "S001", "Evergreen A-class", "42", "false"));
            manager.addVehicle(VehicleFactory.createVehicle("CargoShip", "S002", "Royal Clipper", "37", "false"));

        } catch (Exception e) {
            System.err.println("Error initializing demo fleet: " + e.getMessage());
        }
    }
}