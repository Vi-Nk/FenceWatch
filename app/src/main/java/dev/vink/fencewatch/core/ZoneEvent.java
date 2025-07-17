package dev.vink.fencewatch.core;

import java.time.Instant;

public class ZoneEvent {

    private String deviceId;
    private String zoneId;
    private String event; // "ENTER" or "EXIT"
    private Instant timestamp;

    public ZoneEvent() {
    }

    public ZoneEvent(String deviceId, String zoneId, String event, Instant timestamp) {
        this.deviceId = deviceId;
        this.zoneId = zoneId;
        this.event = event;
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
                "deviceId='" + deviceId + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", event='" + event + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
