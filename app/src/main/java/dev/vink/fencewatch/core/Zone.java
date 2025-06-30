package dev.vink.fencewatch.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Zones")
public class Zone {

    @Id
    @Column(name = "zone_name", nullable = false, unique = true)
    private String zoneName;

    @Column(name = "center_lat")
    private float centerLat;

    @Column(name = "center_long")
    private float centerLong;

    @Column(name = "radius_mtr")
    private int radiusMeter;

    public Zone() {
    }

    public Zone(String zoneName, float centerLat, float centerLong, int radiusMeter) {
        this.zoneName = zoneName;
        this.centerLat = centerLat;
        this.centerLong = centerLong;
        this.radiusMeter = radiusMeter;
    }

    public String getZoneName() {
        return zoneName;
    }

    public float getCenterLat() {
        return centerLat;
    }

    public float getCenterLong() {
        return centerLong;
    }

    public int getRadiusMeter() {
        return radiusMeter;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public void setCenterLat(float centerLat) {
        this.centerLat = centerLat;
    }

    public void setCenterLong(float centerLong) {
        this.centerLong = centerLong;
    }

    public void setRadiusMeter(int radiusMeter) {
        this.radiusMeter = radiusMeter;
    }

}
