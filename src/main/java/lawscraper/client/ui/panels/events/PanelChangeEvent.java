package lawscraper.client.ui.panels.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class PanelChangeEvent extends GwtEvent<PanelChangeHandler> {
    private Object object;
    private final PanelChangeType panelChangeType;
    public final static Type<PanelChangeHandler> TYPE = new Type<PanelChangeHandler>();

    public PanelChangeEvent(Object object, PanelChangeType panelChangeType) {
        this.object = object;
        this.panelChangeType = panelChangeType;
    }

    @Override
    public Type<PanelChangeHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PanelChangeHandler handler) {
        handler.onPanelChange(this);
    }

    public enum PanelChangeType {
        openDescription, closeDescription, openReference, closeReference, gotoDocument
    }

    public static Type<PanelChangeHandler> getType() {
        return TYPE;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public PanelChangeType getPanelChangeType() {
        return panelChangeType;
    }
}
