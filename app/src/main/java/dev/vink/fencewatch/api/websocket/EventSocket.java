package dev.vink.fencewatch.api.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebSocket
public class EventSocket {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Session connected" + session);
    }

    @OnWebSocketMessage
    public void onWebSocketMessage(Session session, String msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode inputJsonNode = mapper.readTree(msg);
            String device = inputJsonNode.get("deviceID").asText();
            DeviceSessionRegistry.registerSession(device, session);
            System.out.println("Registered session of deviceID: " + device);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onWebSocketClose(Session session, int statusCode, String reason) {
        DeviceSessionRegistry.unregisterSession(session);
        System.out.println("Session closed " + session);
    }

    @OnWebSocketError
    public void onWebSocketError(Session session, Throwable error) {
        DeviceSessionRegistry.unregisterSession(session);
        System.out.println("Session errorred out: " + error.getLocalizedMessage());
    }

}
