package ui;

import model.Event;
import model.EventLog;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        DraftGUI gui = new DraftGUI();
        gui.setVisible(true);

        // EFFECTS: Add a shutdownHook to print out the event log
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Iterator<Event> eventLog = EventLog.getInstance().iterator();
            while (eventLog.hasNext()) {
                Event event = eventLog.next();
                System.out.println(event.toString());
            }
        }));

    }

}
