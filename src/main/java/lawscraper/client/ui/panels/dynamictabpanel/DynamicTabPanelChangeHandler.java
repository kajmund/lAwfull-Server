package lawscraper.client.ui.panels.dynamictabpanel;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public interface DynamicTabPanelChangeHandler extends EventHandler {
    void onDynamicTabPanelChange(DynamicTabPanelChangeEvent dynamicTabPanelChangeEvent);
}