package lawscraper.client.ui.lawdisplay.paragraphdisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.server.entities.law.Paragraph;


public class ParagraphDisplay extends Composite {
    private static ParagraphDisplayUiBinder uiBinder = GWT.create(ParagraphDisplayUiBinder.class);

    interface ParagraphDisplayUiBinder extends UiBinder<Widget, ParagraphDisplay> {
    }

    @UiField FlowPanel sectionListItemContainer;
    @UiField FlowPanel sectionContainer;
    @UiField FlowPanel paragraphContainer;

    public ParagraphDisplay() {
        initWidget(uiBinder.createAndBindUi(this));
        setContainer();
    }

    public void setParagraph(Paragraph paragraph) {
        paragraphContainer.add(new HTMLPanel(paragraph.getParagraphNo()));
    }

    private void setContainer() {
        
        paragraphContainer.addDomHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                paragraphContainer.getElement().getStyle().setBackgroundColor("lightgrey");
            }
        }, MouseOverEvent.getType());

        paragraphContainer.addDomHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                paragraphContainer.getElement().getStyle().setBackgroundColor("white");
            }
        }, MouseOutEvent.getType());
    }

}
