package dev.vink.fencewatch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.vink.fencewatch.core.Zone;

public class SpatialIndex {
    private final Map<String, List<Zone>> grid = new ConcurrentHashMap<>();

    public void addZone(Zone z) {
        String key = cellKey(z.getCenterLat(), z.getCenterLong());
        grid.computeIfAbsent(key, k -> new ArrayList<>()).add(z);
    }

    public List<Zone> getCandidateZones(double lat, double lon) {
        String key = cellKey(lat, lon);
        return grid.getOrDefault(key, List.of());
    }

    private String cellKey(double lat, double lon) {
        int latBin = (int) (lat * 10);
        int lonBin = (int) (lon * 10);
        return latBin + ":" + lonBin;
    }
}
