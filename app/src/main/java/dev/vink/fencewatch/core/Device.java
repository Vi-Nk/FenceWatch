package dev.vink.fencewatch.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Devices")
public class Device {

    @Id
    @Column(name = "device_id", nullable = false, unique = true)
    private String deviceID;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "driver_name", nullable = true)
    private String driverName;

    public Device() {
    }

    public Device(String deviceID, String vehicleType, String driverName) {
        this.deviceID = deviceID;
        this.driverName = driverName;
        this.vehicleType = vehicleType;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

}
