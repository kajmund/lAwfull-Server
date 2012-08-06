package lawscraper.client.ui.panels.shortsearchresultpanel;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class SearchChangeEvent extends GwtEvent<SearchChangeHandler> {
    private String query;

    public SearchChangeEvent() {
        super();
    }

    public final static Type<SearchChangeHandler> TYPE = new Type<SearchChangeHandler>();

    public SearchChangeEvent(String query) {
        this.query = query;

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
}
