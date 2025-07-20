package dev.vink.fencewatch.service;

import java.io.IOException;
import java.util.concurrent.*;

import org.eclipse.jetty.websocket.api.Session;

import dev.vink.fencewatch.api.websocket.DeviceSessionRegistry;
import dev.vink.fencewatch.core.ZoneEvent;

public class EventBroadcaster {

    private final BlockingQueue<ZoneEvent> eventQueue = new LinkedBlockingQueue<>();
    private final ExecutorService broadcastWorkers;
    private final ScheduledExecutorService heartbeaScheduler = Executors.newSingleThreadScheduledExecutor();

    public EventBroadcaster(int threadCount) {
        this.broadcastWorkers = Executors.newFixedThreadPool(threadCount);
        heartbeaScheduler.scheduleAtFixedRate(DeviceSessionRegistry::sendHeartbeats, 30, 30, TimeUnit.SECONDS);
        for (int i = 0; i < threadCount; i++) {
            broadcastWorkers.submit(this::processQueue);
        }
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
        for (Session session : DeviceSessionRegistry.getSessions(event.getDeviceID())) {
            String json = buildJson(event);
            if (session.isOpen()) {
                try {
                    session.getRemote().sendString(json);
                } catch (IOException e) {
                    // remove bad sessions or let session registry handle?
                }
            }
        }
    }

    private String buildJson(ZoneEvent event) {
        return "{\n" +
                "  \"deviceID\": \"" + event.getDeviceID() + "\",\n" +
                "  \"zoneId\": \"" + event.getZoneId() + "\",\n" +
                "  \"event\": \"" + event.getEvent() + "\",\n" +
                "  \"timestamp\": " + event.getTimestamp().getEpochSecond() +
                "}";
    }

}
