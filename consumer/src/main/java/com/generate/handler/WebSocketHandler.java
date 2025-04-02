package com.generate.handler;

import com.generate.model.PassGenResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //No need to impl Here
    }

    public void sendUpdate(final PassGenResponse passGenResponse) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage("Pass generated: " + passGenResponse.getId()));
            } catch (Exception e) {
                sessions.remove(session);
            }
        }
    }

}