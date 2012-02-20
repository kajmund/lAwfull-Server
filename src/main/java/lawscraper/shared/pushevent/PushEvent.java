package lawscraper.shared.pushevent;

import com.google.gwt.user.client.rpc.IsSerializable;
import de.novanic.eventservice.client.event.Event;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/8/11
 * Time: 10:06 PM
 */
public class PushEvent implements Event, IsSerializable {
    String message;

    public PushEvent(String message){
        this.message = message;
    }

    public PushEvent() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
