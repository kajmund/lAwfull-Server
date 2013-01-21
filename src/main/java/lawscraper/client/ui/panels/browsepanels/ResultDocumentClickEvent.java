package lawscraper.client.ui.panels.browsepanels;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class ResultDocumentClickEvent extends GwtEvent<SearchLawClickHandler> {
    private String lawKey;

    public ResultDocumentClickEvent() {
        super();
    }

    public final static Type<SearchLawClickHandler> TYPE = new Type<SearchLawClickHandler>();

    public ResultDocumentClickEvent(String lawKey) {
        this.lawKey = lawKey;

    }

    @Override
    public Type<SearchLawClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SearchLawClickHandler handler) {
        handler.onSearchLawClick(this);
    }

    public static Type<SearchLawClickHandler> getType() {
        return TYPE;
    }

    public String getLawKey() {
        return lawKey;
    }
}
