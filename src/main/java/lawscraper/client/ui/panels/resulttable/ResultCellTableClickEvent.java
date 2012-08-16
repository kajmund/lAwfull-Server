package lawscraper.client.ui.panels.resulttable;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class ResultCellTableClickEvent extends GwtEvent<ResultCellTableClickHandler> {
    private String lawKey;

    public ResultCellTableClickEvent() {
        super();
    }

    public final static Type<ResultCellTableClickHandler> TYPE = new Type<ResultCellTableClickHandler>();

    public ResultCellTableClickEvent(String lawKey) {
        this.lawKey = lawKey;

    }

    @Override
    public Type<ResultCellTableClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ResultCellTableClickHandler handler) {
        handler.onResultClick(this);
    }

    public static Type<ResultCellTableClickHandler> getType() {
        return TYPE;
    }

    public String getLawKey() {
        return lawKey;
    }
}
