package lawscraper.client.ui.panels.events;

import com.google.gwt.event.shared.HasHandlers;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/4/12
 * Time: 10:24 PM
 */
public interface HasPanelChangeHandler extends HasHandlers {
    public void addPanelChangeHandler(PanelChangeHandler handler);
}
