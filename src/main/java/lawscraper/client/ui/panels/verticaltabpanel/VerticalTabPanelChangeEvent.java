package lawscraper.client.ui.panels.verticaltabpanel;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class VerticalTabPanelChangeEvent extends GwtEvent<VerticalTabPanelChangeHandler> {
    private Widget widget;

    public VerticalTabPanelChangeEvent() {
        super();
    }

    public final static Type<VerticalTabPanelChangeHandler> TYPE = new Type<VerticalTabPanelChangeHandler>();

    public VerticalTabPanelChangeEvent(Widget widget) {
        this.widget = widget;

    }

    @Override
    public Type<VerticalTabPanelChangeHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(VerticalTabPanelChangeHandler handler) {
        handler.onVerticalTabPanelChange(this);
    }

    public static Type<VerticalTabPanelChangeHandler> getType() {
        return TYPE;
    }

    public Widget getWidget() {
        return widget;
    }
}
