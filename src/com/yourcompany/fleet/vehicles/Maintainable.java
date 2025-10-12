package com.yourcompany.fleet.vehicles;
public interface Maintainable {
    void scheduleMaintenance();
    boolean needsMaintenance();
    void performMaintenance();
    void setMaintenanceStatus(boolean status);
}