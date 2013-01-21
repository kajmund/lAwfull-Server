package lawscraper.client.ui.panels.celltables;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class CellTableClickEvent extends GwtEvent<CellTableClickHandler> {
    private String documentKey;

    public CellTableClickEvent() {
        super();
    }

    public final static Type<CellTableClickHandler> TYPE = new Type<CellTableClickHandler>();

    public CellTableClickEvent(String documentKey) {
        this.documentKey = documentKey;

    }

    @Override
    public Type<CellTableClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CellTableClickHandler handler) {
        handler.onResultClick(this);
    }

    public static Type<CellTableClickHandler> getType() {
        return TYPE;
    }

    public String getDocumentKey() {
        return documentKey;
    }
}
