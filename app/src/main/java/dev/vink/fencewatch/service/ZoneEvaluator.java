package dev.vink.fencewatch.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import dev.vink.fencewatch.core.Location;
import dev.vink.fencewatch.core.Zone;
import dev.vink.fencewatch.core.ZoneEvent;

public class ZoneEvaluator {

    private final SpatialIndex spatialIndex;
    private final EventBroadcaster broadcaster;
    private final ExecutorService threadPool;
    private final Map<String, Set<String>> deviceZoneMap;

    public ZoneEvaluator(SpatialIndex index, EventBroadcaster broadcaster, int threadCount) {
        this.spatialIndex = index;
        this.broadcaster = broadcaster;
        this.deviceZoneMap = new ConcurrentHashMap<>();
        this.threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public void evaluate(Location location) {
        threadPool.submit(() -> handleEvaluation(location));
    }

    private void handleEvaluation(Location location) {
        List<Zone> candidates = spatialIndex.getCandidateZones(location.getLatitude(), location.getLongitude());
        Set<String> nowInZones = new HashSet<>();

        for (Zone zone : candidates) {
            if (isInZone(location, zone)) {
                nowInZones.add(zone.getZoneName());
            }
        }

        Set<String> prevZones = deviceZoneMap.getOrDefault(location.getDeviceID(), Collections.emptySet());

        Set<String> exitedZones = new HashSet<>(prevZones);
        exitedZones.removeAll(nowInZones);

        Set<String> enteredZones = new HashSet<>(nowInZones);
        enteredZones.removeAll(prevZones);

        for (String zoneId : exitedZones) {
            broadcaster.enqueue(new ZoneEvent(location.getDeviceID(), zoneId, "EXIT", location.getTimestamp()));
        }

        for (String zoneId : enteredZones) {
            broadcaster.enqueue(new ZoneEvent(location.getDeviceID(), zoneId, "ENTER", location.getTimestamp()));
        }

        deviceZoneMap.put(location.getDeviceID(), nowInZones);
    }

    private boolean isInZone(Location loc, Zone zone) {
        return boundingBoxPass(loc, zone) &&
                haversine(loc.getLatitude(), loc.getLongitude(), zone.getCenterLat(), zone.getCenterLong()) <= zone
                        .getRadiusMeter();
    }

    private boolean boundingBoxPass(Location loc, Zone zone) {
        double delta = zone.getRadiusMeter() / 111_000.0;
        return Math.abs(loc.getLatitude() - zone.getCenterLat()) <= delta &&
                Math.abs(loc.getLongitude() - zone.getCenterLong()) <= delta;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371_000; // Earth radius in meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
