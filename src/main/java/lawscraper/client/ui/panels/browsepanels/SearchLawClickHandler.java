package lawscraper.client.ui.panels.browsepanels;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public interface SearchLawClickHandler extends EventHandler {
    void onSearchLawClick(ResultDocumentClickEvent event);
}