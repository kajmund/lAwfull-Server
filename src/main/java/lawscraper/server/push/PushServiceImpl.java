package lawscraper.server.push;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/8/11
 * Time: 8:53 PM
 */
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.service.RemoteEventServiceServlet;
import lawscraper.shared.pushevent.PushEvent;

/**
 * This is the Service that receives the RPC from the client. Instead of the default RemoteServiceServlet we
 * extend this one with RemoteEventServiceServlet from the GWTEventService lib. This gives you the ability
 * to send Events to the clients.
 *
 */
public class PushServiceImpl extends RemoteEventServiceServlet {

    /**
     * The Domain that this Service sends Events to.
     */
    private static final Domain DOMAIN = DomainFactory.getDomain("my_domain");

    /**
     * A normal function that gets called when a client sends a message to the server.
     */
    public void sendMessage(String message) {
        /**
         * We dont save the incomming messages on the server. We just send an MyEvent to all
         * registered Listeners on the Domain.
         */
        this.addEvent(DOMAIN, new PushEvent(message));
    }

}
