package lawscraper.client.ui.panels.utilities;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 3/4/12
 * Time: 6:58 PM
 */
public class ElementWrapper extends Widget implements HasClickHandlers {

    public ElementWrapper(Element theElement) {
        setElement(theElement);
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    public void onAttach() {
        if (!super.isAttached()) {
            super.onAttach();
        }
    }
}