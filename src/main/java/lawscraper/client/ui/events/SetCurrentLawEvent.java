package lawscraper.client.ui.events;

import com.google.gwt.event.shared.GwtEvent;
import lawscraper.shared.proxies.HTMLProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 5/16/12
 * Time: 10:08 AM
 */
public class SetCurrentLawEvent extends GwtEvent<SetCurrentLawEventHandler> {
    public static Type<SetCurrentLawEventHandler> TYPE = new Type<SetCurrentLawEventHandler>();

    public HTMLProxy getResult() {
        return result;
    }

    private HTMLProxy result;

    public SetCurrentLawEvent(HTMLProxy result) {
        this.result = result;
    }

    @Override
    public Type<SetCurrentLawEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetCurrentLawEventHandler handler) {
        handler.onSetCurrentLaw(this);
    }
}
