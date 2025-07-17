package dev.vink.fencewatch.service;

import java.io.IOException;
import java.util.concurrent.*;

import org.eclipse.jetty.websocket.api.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.vink.fencewatch.api.websocket.DeviceSessionRegistry;
import dev.vink.fencewatch.core.ZoneEvent;

public class EventBroadcaster {

    private final BlockingQueue<ZoneEvent> eventQueue = new LinkedBlockingQueue<>();

    public EventBroadcaster() {
        Thread worker = new Thread(this::processQueue, "EventBroadcaster-Worker");
        worker.setDaemon(true);
        worker.start();
    }

    public void enqueue(ZoneEvent event) {
        eventQueue.offer(event);
    }

    private void processQueue() {
        while (true) {
            try {
                ZoneEvent event = eventQueue.take(); // blocking
                broadcast(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void broadcast(ZoneEvent event) {
        String json = serializeToJson(event);
        for (Session session : DeviceSessionRegistry.getSessions(event.getDeviceId())) {
            if (session.isOpen()) {
                try {
                    session.getRemote().sendString(json);
                } catch (IOException e) {
                    // remove bad sessions ?
                }
            }
        }
    }

    private String serializeToJson(ZoneEvent event) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize ZoneEvent to JSON", e);
        }
    }
}
