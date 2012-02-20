package lawscraper.shared.pushevent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/8/11
 * Time: 10:18 PM
 */
public class PushListener implements RemoteEventListener {

    /**
     * This function gets called by EventService
     */
    public void apply(Event anEvent) {
        /**
         * Check if the incoming Event is from the type MyEvent and if so, call the corresponding function
         */
        if(anEvent instanceof PushEvent)
            onMyEvent((PushEvent)anEvent);
    }

    /**
     * This function gets called when the incomming Event is from the Type MyEvent
     *
     * @param event
     */
    public void onMyEvent(PushEvent event){}

}
