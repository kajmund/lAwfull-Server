package lawscraper.client.ui.panels.shortsearchresultpanel;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 7/13/12
 * Time: 6:19 PM
 */
public interface SearchChangeHandler extends EventHandler {
    void onSearchChange(SearchChangeEvent event);
}