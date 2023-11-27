package org.atar.supportsys.event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventBroker {

    private static final Queue<Event> EVENTS = new LinkedList<>();
    public static synchronized List<Event> getLatestEvents() {
        final ArrayList<Event> eventsToReturn = new ArrayList<>(EVENTS);
        EVENTS.clear();
        return eventsToReturn;
    }
    public static synchronized void addEvent(Event ev) { EVENTS.add(ev); }
}
