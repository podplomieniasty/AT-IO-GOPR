package org.atar.supportsys.broker;


import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.atar.simulator.Simulation;
import org.atar.simulator.clock.SimulationClock;
import org.atar.supportsys.event.Event;
import org.atar.utils.SettingsVariables;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

public abstract class Publisher {

    private static Channel channel, offlineChannel;

    public static void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(SettingsVariables.HOST);
            factory.setPort(SettingsVariables.PORT);

            Connection con = factory.newConnection();
            channel = con.createChannel();
            offlineChannel = con.createChannel();
            channel.exchangeDeclare(SettingsVariables.RABBITMQ_EXCHANGE_NAME, "direct");
            offlineChannel.exchangeDeclare(SettingsVariables.RABBITMQ_OFFLINE_EXCHANGE_NAME, "direct");
        } catch (IOException ioe) {
            System.out.println("[Publisher] Caught IOException while initializing!");
            ioe.printStackTrace();
        } catch (TimeoutException te) {
            System.out.println("[Publisher] Caught TimeoutException while initializing!");
            te.printStackTrace();
        }
    }

    public static void publish(PublisherType topic, String msg) {
        try {
            if(Simulation.isRunning()) {
                channel.basicPublish(SettingsVariables.RABBITMQ_EXCHANGE_NAME,
                                    topic.toString(), null, msg.getBytes());
            } else {
                offlineChannel.basicPublish(SettingsVariables.RABBITMQ_OFFLINE_EXCHANGE_NAME,
                                            topic.toString(), null, msg.getBytes());
            }
        } catch (IOException ioe) {
            System.out.println("[Publisher] Caught IOException while publishing!");
            ioe.printStackTrace();
        }
    }

    public static void publishEvent(PublisherType topic, String msg) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        LocalDateTime currentDate = LocalDateTime.now();
        String realTime = currentDate.format(formatter);
        // pobranie czasu z zegara symulacji
        String simTime = SimulationClock.getClockCopy().toString();

        Gson gson = new Gson();
        Event ev = new Event(topic.toString(), realTime, simTime, msg);
        String eventAsJson = gson.toJson(ev);
        publish(PublisherType.SYSTEM_EVENTS, eventAsJson);

    }
}
