package lawscraper.client.ui.panels.documentsearchpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.client.ui.panels.events.HasPanelChangeHandler;
import lawscraper.client.ui.panels.events.PanelChangeEvent;
import lawscraper.client.ui.panels.events.PanelChangeHandler;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 3/15/12
 * Time: 10:20 PM
 */
public class DocumentSearchPanel extends Composite implements HasPanelChangeHandler {
    private static BoxPanelUiBinder uiBinder = GWT.create(BoxPanelUiBinder.class);
    @UiField FlowPanel resultSet;
    private PanelChangeHandler handler;

    @Override
    public void addPanelChangeHandler(PanelChangeHandler handler) {
        this.handler = handler;
        this.addHandler(handler, PanelChangeEvent.getType());
    }

    interface BoxPanelUiBinder extends UiBinder<FlowPanel, DocumentSearchPanel> {
    }

    public DocumentSearchPanel() {
        initWidget(uiBinder.createAndBindUi(this));

        for (int i = 0; i < 10; i++) {
            final DocumentResultElement docResult = new DocumentResultElement();
            docResult.setDocumentId("RK_2010:3");
            PanelChangeHandler panelChangeHandler = new PanelChangeHandler() {
                @Override
                public void onPanelChange(PanelChangeEvent event) {
                    event.setObject(docResult);
                    handler.onPanelChange(event);
                }
            };

            docResult.addPanelChangeHandler(panelChangeHandler);
            resultSet.add(docResult);
        }
    }
}
