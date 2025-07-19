package dev.vink.fencewatch.core;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "ZoneEvent")
public class ZoneEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private String deviceID;

    @Column(name = "zone_id", nullable = false)
    private String zoneId;

    @Column(name = "event", nullable = false)
    private String event; // "ENTER" or "EXIT"

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public ZoneEvent() {
    }

    public ZoneEvent(String deviceID, String zoneId, String event, Instant timestamp) {
        this.deviceID = deviceID;
        this.zoneId = zoneId;
        this.event = event;
        this.timestamp = timestamp;
    }

    public String getdeviceID() {
        return deviceID;
    }

    public void setdeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ZoneEvent{" +
                "deviceID='" + deviceID + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", event='" + event + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
