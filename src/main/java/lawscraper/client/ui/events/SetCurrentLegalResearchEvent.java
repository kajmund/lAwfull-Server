package lawscraper.client.ui.events;

import com.google.gwt.event.shared.GwtEvent;
import lawscraper.shared.proxies.LegalResearchProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/16/12
 * Time: 10:08 AM
 */
public class SetCurrentLegalResearchEvent extends GwtEvent<SetCurrentLegalResearchEventHandler> {
    public static Type<SetCurrentLegalResearchEventHandler> TYPE = new Type<SetCurrentLegalResearchEventHandler>();
    private final LegalResearchProxy legalResearchProxy;

    public SetCurrentLegalResearchEvent(LegalResearchProxy legalResearchProxy) {
        this.legalResearchProxy = legalResearchProxy;
    }

    @Override
    public Type<SetCurrentLegalResearchEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SetCurrentLegalResearchEventHandler handler) {
        handler.onSetCurrentLegalResearch(this);
    }

    public LegalResearchProxy getLegalResearchProxy() {
        return legalResearchProxy;
    }
}
