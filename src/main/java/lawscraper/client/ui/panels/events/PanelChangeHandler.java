package lawscraper.client.ui.panels.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public interface PanelChangeHandler extends EventHandler {
    void onPanelChange(PanelChangeEvent event);
}