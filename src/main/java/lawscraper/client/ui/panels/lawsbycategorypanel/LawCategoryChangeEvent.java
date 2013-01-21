package lawscraper.client.ui.panels.lawsbycategorypanel;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 7/13/12
 * Time: 6:19 PM
 */
public class LawCategoryChangeEvent extends GwtEvent<LawCategoryChangeHandler> {
    private String lawKey;

    public LawCategoryChangeEvent() {
        super();
    }

    public final static Type<LawCategoryChangeHandler> TYPE = new Type<LawCategoryChangeHandler>();

    public LawCategoryChangeEvent(String lawKey) {
        this.lawKey = lawKey;

    }

    @Override
    public Type<LawCategoryChangeHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LawCategoryChangeHandler handler) {
        handler.onCategoryChange(this);
    }

    public static Type<LawCategoryChangeHandler> getType() {
        return TYPE;
    }

    public String getLawKey() {
        return lawKey;
    }
}
