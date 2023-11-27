package org.atar.simulator;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;

@ServerEndpoint("/output")
public class OutputSocket {

    public static final int MS_BETWEEN_UPDATES = 30;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("[OutputSocket] Opened OutputSocked: " + session.getId());
    }

    @OnMessage
    public void onMessage(String txt, Session session) throws IOException {
        System.out.println("[OutputSocket] Message received: " + txt);

    }

}
