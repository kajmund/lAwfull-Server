package lawscraper.client.ui.panels.dynamictabpanel;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class DynamicTabPanelChangeEvent extends GwtEvent<DynamicTabPanelChangeHandler> {
    private Widget widget;

    public DynamicTabPanelChangeEvent() {
        super();
    }

    public final static Type<DynamicTabPanelChangeHandler> TYPE = new Type<DynamicTabPanelChangeHandler>();

    public DynamicTabPanelChangeEvent(Widget widget) {
        this.widget = widget;

    }

    @Override
    public Type<DynamicTabPanelChangeHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DynamicTabPanelChangeHandler handler) {
        handler.onDynamicTabPanelChange(this);
    }

    public static Type<DynamicTabPanelChangeHandler> getType() {
        return TYPE;
    }

    public Widget getWidget() {
        return widget;
    }
}
