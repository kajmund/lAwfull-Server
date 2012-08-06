package lawscraper.client.ui.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/16/12
 * Time: 10:08 AM
 */
public class SetCurrentLawEvent extends GwtEvent<SetCurrentLawEventHandler> {
    public static Type<SetCurrentLawEventHandler> TYPE = new Type<SetCurrentLawEventHandler>();
    private final FlowPanel flerpContainer;

    public SetCurrentLawEvent(FlowPanel flerpContainer) {
        this.flerpContainer = flerpContainer;
    }

    @Override
    public Type<SetCurrentLawEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetCurrentLawEventHandler handler) {
        handler.onSetCurrentLaw(this);
    }

    public FlowPanel getFlerpContainer() {
        return flerpContainer;
    }
}
