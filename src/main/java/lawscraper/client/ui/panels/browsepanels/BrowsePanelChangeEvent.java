package lawscraper.client.ui.panels.browsepanels;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class BrowsePanelChangeEvent extends GwtEvent<SearchChangeHandler> {
    private String query;
    private boolean searchRequest;

    public BrowsePanelChangeEvent() {
        super();
    }

    public final static Type<SearchChangeHandler> TYPE = new Type<SearchChangeHandler>();

    public BrowsePanelChangeEvent(String query) {
        this.query = query;

    }

    public BrowsePanelChangeEvent(String query, boolean searchRequest) {
        this.query = query;
        this.searchRequest = searchRequest;
    }

    @Override
    public Type<SearchChangeHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SearchChangeHandler handler) {
        handler.onSearchChange(this);
    }

    public static Type<SearchChangeHandler> getType() {
        return TYPE;
    }

    public String getQuery() {
        return query;
    }

    public boolean isSearchRequest() {
        return searchRequest;
    }
}
