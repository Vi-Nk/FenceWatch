package dev.vink.fencewatch.api.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

public class DeviceSessionRegistry {

    private static final ConcurrentHashMap<String, Set<Session>> deviceSessions = new ConcurrentHashMap<>();

    public static void registerSession(String device, Session session) {
        Set<Session> sessions = deviceSessions.get(device);

        if (sessions == null) {
            sessions = ConcurrentHashMap.newKeySet();
            // Handle possible race condition for fresh set creation
            Set<Session> current = deviceSessions.putIfAbsent(device, sessions);

            if (current != null) {
                sessions = current;
            }
        }

        sessions.add(session);
    }

    public static void unregisterSession(Session session) {
        deviceSessions.forEach((device, sessions) -> sessions.remove(session));
    }

    public static Set<Session> getSessions(String device) {
        return deviceSessions.getOrDefault(device, Collections.emptySet());
    }

    public static void sendHeartbeats() {
        for (String device : deviceSessions.keySet()) {
            for (Session session : getSessions(device)) {
                if (session.isOpen()) {
                    try {
                        session.getRemote().sendPing(ByteBuffer.wrap(new byte[] { 1 }));
                    } catch (IOException e) {
                        unregisterSession(session);
                    }
                } else {
                    unregisterSession(session);
                }
            }
        }
    }

}
